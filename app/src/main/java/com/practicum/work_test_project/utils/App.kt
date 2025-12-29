package com.practicum.work_test_project.utils

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.practicum.work_test_project.di.dataModule
import com.practicum.work_test_project.di.interactorsModule
import com.practicum.work_test_project.di.networkModule
import com.practicum.work_test_project.di.repositoryModule
import com.practicum.work_test_project.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidContext(this@App)

            modules(
                dataModule, interactorsModule, networkModule, repositoryModule, viewModelsModule
            )
        }

    }
}