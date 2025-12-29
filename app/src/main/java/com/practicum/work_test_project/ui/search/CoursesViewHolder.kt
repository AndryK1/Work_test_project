package com.practicum.work_test_project.ui.search

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.practicum.work_test_project.R
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.work_test_project.domain.entity.Course

class CoursesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    val previewPhoto = itemView.findViewById<ImageView>(R.id.previewPhoto)
    val rating = itemView.findViewById<TextView>(R.id.rateText)
    val publishDate = itemView.findViewById<TextView>(R.id.publishedDate)
    val title = itemView.findViewById<TextView>(R.id.title)
    val description = itemView.findViewById<TextView>(R.id.description)
    val price = itemView.findViewById<TextView>(R.id.price)

    fun bind(courseData: Course){

        rating.text = courseData.rate
        publishDate.text = courseData.publishDate
        title.text = courseData.title
        description.text = courseData.description
        price.text = courseData.price + " ₽"

        // Проверяем текущие размеры ImageView (пришлось добавить для корректной загрузки фото)
        if (previewPhoto.width > 0 && previewPhoto.height > 0) {
            // Размеры уже известны - загружаем сразу
            //Т.к. фото у нас в api пока не предусмотрено, просто сразу кидаем plaseholder
            setImageVisible(false, "")
        } else {
            // Размеры неизвестны - ждем измерения
            previewPhoto.post {
                if (previewPhoto.width > 0 && previewPhoto.height > 0) {
                    setImageVisible(false, "")
                } else {
                    // Если всё равно 0, используем фиксированные размеры из layout
                    loadImageWithFixedSize()
                }
            }
        }
    }

    private fun loadImageWithFixedSize() {

        val displayMetrics = itemView.context.resources.displayMetrics
        val heightInPx = (114 * displayMetrics.density).toInt()

        Glide.with(itemView.context)
            .load(R.drawable.photo_placeholder)
            .override(android.view.ViewGroup.LayoutParams.MATCH_PARENT, heightInPx)
            .transform(
                MultiTransformation(
                    RoundedCorners(12)
                )
            )
            .into(previewPhoto)
    }

    private fun setImageVisible(state: Boolean, imagePath: String){
        if(state){

            Glide.with(itemView.context)
                .load(imagePath)
                .transform(
                    MultiTransformation(
                        CenterCrop(),
                        RoundedCorners(12)
                    )
                )
                .into(previewPhoto)
        }
        else{

            Glide.with(itemView.context)
                .load(R.drawable.photo_placeholder)
                .transform(
                    MultiTransformation(
                        CenterCrop(),
                        RoundedCorners(12)
                    )
                )
                .into(previewPhoto)
        }
    }
}