package com.practicum.work_test_project.di

import com.practicum.work_test_project.ui.search.viewModel.ViewModelSearch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel{
        ViewModelSearch(get())
    }
}