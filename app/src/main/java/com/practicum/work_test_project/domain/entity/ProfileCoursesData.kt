package com.practicum.work_test_project.domain.entity

//Просто для удобства отображения mock объектов на экране профиля
data class ProfileCoursesData(
    val title: String,
    val rating: String,
    val date: String,
    val completedLessons: Int,
    val totalLessons: Int,
    val imageRes: Int
)
