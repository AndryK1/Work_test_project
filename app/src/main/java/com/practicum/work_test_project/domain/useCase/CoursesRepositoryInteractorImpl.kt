package com.practicum.work_test_project.domain.useCase

import com.practicum.work_test_project.domain.api.CoursesRepository
import com.practicum.work_test_project.domain.api.CoursesRepositoryInteractor
import com.practicum.work_test_project.domain.entity.Course
import com.practicum.work_test_project.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CoursesRepositoryInteractorImpl(
    val repository: CoursesRepository
) : CoursesRepositoryInteractor {
    override fun searchCourses(): Flow<Pair<List<Course>?, String?>> {

        return repository.searchCourses().map { result ->

            when(result){

                is Resource.Error -> {
                    Pair(null, result.message)
                }

                is Resource.Success -> {
                    Pair(result.data, null)
                }
            }
        }

    }
}