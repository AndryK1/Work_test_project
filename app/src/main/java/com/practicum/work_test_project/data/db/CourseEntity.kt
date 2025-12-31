package com.practicum.work_test_project.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course_table")
data class CourseEntity (
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val title: String,
    val text: String,
    val price: String,
    val rate: Float,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String
)