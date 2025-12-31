package com.practicum.work_test_project.data.dto

data class CourseDto(
    val id: Long,
    val title: String,
    val text: String,
    val price: String,
    val rate: Float,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String
)
