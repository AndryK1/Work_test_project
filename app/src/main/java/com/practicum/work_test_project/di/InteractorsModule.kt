package com.practicum.work_test_project.di

import com.practicum.work_test_project.domain.api.CoursesRepositoryInteractor
import com.practicum.work_test_project.domain.useCase.CoursesRepositoryInteractorImpl
import org.koin.dsl.module

val interactorsModule = module {

    single<CoursesRepositoryInteractor> {
        CoursesRepositoryInteractorImpl(get())
    }
}