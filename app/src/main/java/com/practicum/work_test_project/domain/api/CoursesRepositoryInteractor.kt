package com.practicum.work_test_project.domain.api

import com.practicum.work_test_project.domain.entity.Course
import kotlinx.coroutines.flow.Flow

interface CoursesRepositoryInteractor {

    fun searchCourses() : Flow<Pair<List<Course>?, String?>>
}