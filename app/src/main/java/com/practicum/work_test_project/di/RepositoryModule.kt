package com.practicum.work_test_project.di

import com.practicum.work_test_project.data.search.network.CoursesRepositoryImpl
import com.practicum.work_test_project.domain.api.CoursesRepository
import org.koin.dsl.module

val repositoryModule = module{

    single<CoursesRepository> {
        CoursesRepositoryImpl(get(),get())
    }
}