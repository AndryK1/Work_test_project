package com.practicum.work_test_project.data.db

import com.practicum.work_test_project.data.converters.CoursesDbConverter
import com.practicum.work_test_project.domain.db.LikedHistoryRepository
import com.practicum.work_test_project.domain.entity.Course
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LikedHistoryRepositoryImpl(
    val appDatabase: AppDatabase,
    val converter: CoursesDbConverter
) : LikedHistoryRepository{

    override fun getFavorites(): Flow<List<Course>> = flow {
        val courses = appDatabase.courseDao().getFavorites()
        emit(fromEntityToCourse(courses))
    }

    override suspend fun saveCourse(course: Course) {
        appDatabase.courseDao().insertCourse(fromCourseToEntity(course))
    }

    override suspend fun isCourseInFavorite(courseId: Long): Boolean {
        return appDatabase.courseDao().isCourseInFavorites(courseId) != null
    }

    override suspend fun deleteCourse(course: Course) {
        appDatabase.courseDao().deleteCourse(fromCourseToEntity(course))
    }

    private fun fromEntityToCourse(courses : List<CourseEntity>) : List<Course>{
        return courses.map { course -> converter.map(course) }
    }

    private fun fromCourseToEntity(course : Course) : CourseEntity{
        return converter.map(course)
    }
}