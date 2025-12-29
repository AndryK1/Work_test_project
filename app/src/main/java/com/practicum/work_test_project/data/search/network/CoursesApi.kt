package com.practicum.work_test_project.data.search.network

import com.practicum.work_test_project.data.search.dto.CoursesResponse
import retrofit2.http.GET

//Пока получается фейк
interface CoursesApi {
    @GET("courses")
    suspend fun getCourses() : CoursesResponse
}