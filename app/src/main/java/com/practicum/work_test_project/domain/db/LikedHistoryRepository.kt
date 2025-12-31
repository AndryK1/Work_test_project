package com.practicum.work_test_project.domain.db

import com.practicum.work_test_project.domain.entity.Course
import kotlinx.coroutines.flow.Flow

interface LikedHistoryRepository {

    fun getFavorites(): Flow<List<Course>>

    suspend fun saveCourse(course: Course)

    suspend fun deleteCourse(course: Course)

    suspend fun isCourseInFavorite(courseId: Long): Boolean
}