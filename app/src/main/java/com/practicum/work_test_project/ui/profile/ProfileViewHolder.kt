package com.practicum.work_test_project.ui.profile

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.practicum.work_test_project.R
import com.practicum.work_test_project.domain.entity.ProfileCoursesData

class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val title: TextView = itemView.findViewById(R.id.profileTitle)
    val rating: TextView = itemView.findViewById(R.id.rateText)
    val publishedDate: TextView = itemView.findViewById(R.id.publishedDate)

    val progressPercent: TextView = itemView.findViewById(R.id.progressPercent)
    val progressLessons: TextView = itemView.findViewById(R.id.progressLessons)
    val progressBar: ProgressBar = itemView.findViewById(R.id.progressBarLine)

    fun bind(profile: ProfileCoursesData){
        title.text = profile.title
        rating.text = profile.rating
        publishedDate.text = profile.date

        val percent = (profile.completedLessons * 100) / profile.totalLessons

        progressBar.progress = percent
        progressPercent.text = "$percent%"
        progressLessons.text = "${profile.completedLessons}/${profile.totalLessons} уроков"
    }
}
