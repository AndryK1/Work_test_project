package com.practicum.work_test_project.di

import com.practicum.work_test_project.ui.authorization.viewModel.AuthViewModel
import com.practicum.work_test_project.ui.details.viewModel.DetailsViewModel
import com.practicum.work_test_project.ui.favorites.viewModel.FavoritesViewModel
import com.practicum.work_test_project.ui.search.viewModel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel{
        SearchViewModel(get(), get ())
    }

    viewModel{
        DetailsViewModel(get())
    }

    viewModel{
        FavoritesViewModel(get())
    }

    viewModel {
        AuthViewModel()
    }
}