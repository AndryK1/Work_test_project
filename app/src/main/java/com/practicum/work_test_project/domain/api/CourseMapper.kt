package com.practicum.work_test_project.domain.api

import com.practicum.work_test_project.data.dto.CourseDto
import com.practicum.work_test_project.domain.entity.Course

interface CourseMapper {

    fun map(course: Course) : CourseDto

    fun map(courseDto: CourseDto) : Course
}