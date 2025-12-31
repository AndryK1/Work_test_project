package com.practicum.work_test_project.domain.impl

import com.practicum.work_test_project.domain.db.LikedHistoryInteractor
import com.practicum.work_test_project.domain.db.LikedHistoryRepository
import com.practicum.work_test_project.domain.entity.Course
import kotlinx.coroutines.flow.Flow

class LikedHistoryInteractorImpl(val repository: LikedHistoryRepository) : LikedHistoryInteractor {

    override suspend fun invoke(
        course: Course,
        isFavorite: Boolean
    ): Boolean {
        return when{
            isFavorite -> {
                repository.deleteCourse(course)
                false
            }
            else -> {
                repository.saveCourse(course)
                true
            }
        }
    }

    override suspend fun isCourseInFavorite(courseId: Long): Boolean {
        return repository.isCourseInFavorite(courseId)
    }

    override fun getLikedCourses(): Flow<List<Course>> {
        return repository.getFavorites()
    }
}