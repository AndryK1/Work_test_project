package com.practicum.work_test_project.ui.favorites

import com.practicum.work_test_project.domain.entity.Course

sealed interface FavoritesState {

    object Empty : FavoritesState

    object Loading : FavoritesState

    data class Content(
        val favoritesList: List<Course>
    ) : FavoritesState
}