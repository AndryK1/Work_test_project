package com.practicum.work_test_project.di

import com.practicum.work_test_project.data.search.network.CoursesApi
import com.practicum.work_test_project.data.search.network.interseptors.MockInterceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {
        OkHttpClient.Builder()
            .addInterceptor(MockInterceptor(androidContext()))
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(("https://courses-mock-api.com/"))
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<CoursesApi> {
        get<Retrofit>().create(CoursesApi::class.java)
    }
}