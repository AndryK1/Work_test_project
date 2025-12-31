package com.practicum.work_test_project.domain.api

import com.practicum.work_test_project.domain.entity.Course
import kotlinx.coroutines.flow.Flow

interface LikedHistoryInteractor {

    suspend fun invoke(course: Course, isFavorite: Boolean) : Boolean

    suspend fun isCourseInFavorite(courseId: Long): Boolean

    fun getLikedCourses(): Flow<List<Course>>
}