package com.practicum.work_test_project.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.work_test_project.R
import com.practicum.work_test_project.domain.entity.Course

class CoursesAdapter(
private var courses : List<Course>
) : RecyclerView.Adapter<CoursesViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CoursesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return CoursesViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CoursesViewHolder,
        position: Int
    ) {
        holder.bind(courses[position])
    }

    fun updateList(newCourses: List<Course>) {
        courses = newCourses
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return courses.size
    }
}