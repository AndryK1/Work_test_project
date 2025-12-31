package com.practicum.work_test_project.ui.customViews

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import com.practicum.work_test_project.R

private val Int.dp get() = this * Resources.getSystem().displayMetrics.density

class SocialButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val iconView: ImageView

    init {
        inflate(context, R.layout.view_social_button, this)
        iconView = findViewById(R.id.icon)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.SocialButtonView)

        val icon = ta.getDrawable(R.styleable.SocialButtonView_icon)
        val solidColor = ta.getColor(
            R.styleable.SocialButtonView_solidColor,
            Color.TRANSPARENT
        )

        val start = ta.getColor(
            R.styleable.SocialButtonView_gradientStartColor,
            Color.TRANSPARENT
        )
        val end = ta.getColor(
            R.styleable.SocialButtonView_gradientEndColor,
            Color.TRANSPARENT
        )

        val useGradient = ta.getBoolean(
            R.styleable.SocialButtonView_useGradient,
            false
        )

        ta.recycle()

        iconView.setImageDrawable(icon)

        background = if (useGradient) {
            GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(start, end)
            ).apply {
                cornerRadius = 30.dp
            }
        } else {
            GradientDrawable().apply {
                setColor(solidColor)
                cornerRadius = 30.dp
            }
        }
    }

}
