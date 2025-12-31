package com.practicum.work_test_project.di

import androidx.room.Room
import com.google.gson.Gson
import com.practicum.work_test_project.data.converters.CoursesDbConverter
import com.practicum.work_test_project.data.db.AppDatabase
import com.practicum.work_test_project.domain.api.CourseMapper
import com.practicum.work_test_project.domain.impl.CourseMapperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    factory { Gson() }

    factory<CourseMapper>{
        CourseMapperImpl()
    }

    factory {
        CoursesDbConverter()
    }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database_db")
            .build()
    }
}