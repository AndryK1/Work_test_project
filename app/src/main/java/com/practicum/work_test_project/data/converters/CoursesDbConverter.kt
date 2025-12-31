package com.practicum.work_test_project.data.converters

import com.practicum.work_test_project.data.db.CourseEntity
import com.practicum.work_test_project.domain.entity.Course

class CoursesDbConverter {

    fun map(course: Course) : CourseEntity{
        return CourseEntity(
            id = course.id,
            title = course.title,
            text = course.description,
            price = course.price,
            rate = course.rate.toFloat(),
            startDate = course.startDate,
            hasLike = true,
            publishDate = course.publishDate
        )
    }

    fun map(course: CourseEntity) : Course{
        return Course(
            id = course.id,
            title = course.title,
            description = course.text,
            price = course.price,
            rate = course.rate.toString(),
            startDate = course.startDate,
            isFavorite = course.hasLike,
            publishDate = course.publishDate
        )
    }
}