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

    fun bind(courseData: Course, imageUrl: String){

        rating.text = courseData.rate
        publishDate.text = courseData.publishDate
        title.text = courseData.title
        description.text = courseData.description
        price.text = courseData.price + " ₽"
    }

}