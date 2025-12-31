package com.practicum.work_test_project.data.search.dto

import com.practicum.work_test_project.data.dto.CourseDto

data class CoursesResponse (
    val courses : List<CourseDto>
) : Response()