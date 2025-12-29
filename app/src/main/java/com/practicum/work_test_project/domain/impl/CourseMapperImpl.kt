package com.practicum.work_test_project.domain.impl

import com.practicum.work_test_project.data.dto.CourseDto
import com.practicum.work_test_project.domain.api.CourseMapper
import com.practicum.work_test_project.domain.entity.Course

class CourseMapperImpl : CourseMapper {
    override fun map(course: Course): CourseDto {
        return CourseDto(
            id = course.id,
            title = course.title,
            text = course.description,
            price = course.price,
            rate = course.rate.toFloat(),
            startDate = course.startDate,
            hasLike = course.isFavorite,
            publishDate = course.publishDate
        )
    }

    override fun map(courseDto: CourseDto): Course {
        return Course(
            id = courseDto.id,
            title = courseDto.title,
            description = courseDto.text,
            price = courseDto.price,
            rate = courseDto.rate.toString(),
            startDate = courseDto.startDate,
            isFavorite = courseDto.hasLike,
            publishDate = courseDto.publishDate
        )
    }
}