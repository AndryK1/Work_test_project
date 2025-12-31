package com.practicum.work_test_project.di

import com.practicum.work_test_project.domain.api.CourseMapper
import com.practicum.work_test_project.domain.impl.CourseMapperImpl
import org.koin.dsl.module

val dataModule = module {

    factory<CourseMapper>{
        CourseMapperImpl()
    }
}