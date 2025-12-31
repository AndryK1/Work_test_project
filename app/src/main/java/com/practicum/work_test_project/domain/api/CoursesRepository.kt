package com.practicum.work_test_project.domain.api

import com.practicum.work_test_project.data.dto.CourseDto
import com.practicum.work_test_project.domain.entity.Course
import com.practicum.work_test_project.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {
    fun searchCourses(): Flow<Resource<List<Course>>>
}