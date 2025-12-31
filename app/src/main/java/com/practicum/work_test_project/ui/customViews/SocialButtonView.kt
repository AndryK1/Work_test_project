package com.practicum.work_test_project.ui.customViews

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import com.practicum.work_test_project.R

private val Int.dp get() = this * Resources.getSystem().displayMetrics.density

class SocialButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private var icon: Bitmap? = null
    private var backgroundDrawable: GradientDrawable? = null
    private var useGradient: Boolean = false
    private var solidColor: Int = Color.TRANSPARENT
    private var gradientStart: Int = Color.TRANSPARENT
    private var gradientEnd: Int = Color.TRANSPARENT
    private val iconPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        isClickable = true
        isFocusable = true

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.SocialButtonView,
            defStyleAttr,
            defStyleRes
        )

        try {
            val iconDrawable = typedArray.getDrawable(R.styleable.SocialButtonView_icon)
            icon = iconDrawable?.toBitmap()

            solidColor = typedArray.getColor(
                R.styleable.SocialButtonView_solidColor,
                Color.TRANSPARENT
            )
            gradientStart = typedArray.getColor(
                R.styleable.SocialButtonView_gradientStartColor,
                Color.TRANSPARENT
            )
            gradientEnd = typedArray.getColor(
                R.styleable.SocialButtonView_gradientEndColor,
                Color.TRANSPARENT
            )
            useGradient = typedArray.getBoolean(
                R.styleable.SocialButtonView_useGradient,
                false
            )
            // фон
            backgroundDrawable = if (useGradient) {
                GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    intArrayOf(gradientStart, gradientEnd)
                ).apply {
                    cornerRadius = 30.dp
                }
            } else {
                GradientDrawable().apply {
                    setColor(solidColor)
                    cornerRadius = 30.dp
                }
            }
        } finally {
            typedArray.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minSize = resources.getDimensionPixelSize(R.dimen.min_social_button_size)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> minOf(widthSize, minSize)
            else -> minSize
        }

        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> minOf(heightSize, minSize)
            else -> minSize
        }

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Рисуем фон
        backgroundDrawable?.let { drawable ->
            drawable.setBounds(0, 0, width, height)
            drawable.draw(canvas)
        }

        // иконка по центру
        icon?.let { bitmap ->
            val left = (width - bitmap.width) / 2f
            val top = (height - bitmap.height) / 2f

            canvas.drawBitmap(bitmap, left, top, iconPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                return true
            }
            MotionEvent.ACTION_UP -> {
                if (isEnabled) {
                    performClick()
                }
                return true
            }
        }
        return super.onTouchEvent(event)
    }
    override fun performClick(): Boolean {
        super.performClick()
        return true
    }
}