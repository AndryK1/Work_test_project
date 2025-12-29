package com.practicum.work_test_project.data.search.network

import com.practicum.work_test_project.data.dto.CourseDto
import com.practicum.work_test_project.domain.api.CourseMapper
import com.practicum.work_test_project.domain.api.CoursesRepository
import com.practicum.work_test_project.domain.entity.Course
import com.practicum.work_test_project.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class CoursesRepositoryImpl(
    private val api: CoursesApi,
    private val mapper : CourseMapper
) : CoursesRepository {

    override fun searchCourses(): Flow<Resource<List<Course>>> = flow  {

        try {
            val courses = api.getCourses().courses.map {
                mapper.map(it)
            }

            emit(Resource.Success(courses))
        } catch (e : IOException){
            emit(Resource.Error(
                message = "Не удалось загрузить данные"
            ))
        } catch (e: Exception) {
            emit(Resource.Error(
                message = "Произошла ошибка"
            ))
        }
    }
}