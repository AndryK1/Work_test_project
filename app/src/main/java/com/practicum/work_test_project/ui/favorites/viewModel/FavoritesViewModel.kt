package com.practicum.work_test_project.ui.favorites.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.work_test_project.domain.api.LikedHistoryInteractor
import com.practicum.work_test_project.domain.entity.Course
import com.practicum.work_test_project.ui.favorites.FavoritesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FavoritesViewModel(
    val likedHistoryInteractor: LikedHistoryInteractor
) : ViewModel() {

    private val _favoritesState = MutableStateFlow<FavoritesState>(FavoritesState.Empty)
    val favoriteState : StateFlow<FavoritesState> = _favoritesState.asStateFlow()


    init {
        loadFavorites()
    }

    private fun loadFavorites(){
        _favoritesState.value = FavoritesState.Loading

        viewModelScope.launch {
            likedHistoryInteractor.getLikedCourses().collect { courses ->
                if (courses.isEmpty()) {
                    _favoritesState.value = FavoritesState.Empty
                } else {
                    _favoritesState.value = FavoritesState.Content(courses)
                }
            }

        }
    }

    fun toggleFavorite(course: Course) {
        viewModelScope.launch {
            val isFavorite = likedHistoryInteractor.isCourseInFavorite(course.id)

            val newFavoriteStatus = likedHistoryInteractor.invoke(course, isFavorite)

            loadFavorites()
        }
    }

    suspend fun refreshFavorites() {
        loadFavorites()
    }
}