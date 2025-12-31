package com.practicum.work_test_project.ui.details.viewModel

import androidx.lifecycle.ViewModel
import com.practicum.work_test_project.domain.db.LikedHistoryInteractor
import com.practicum.work_test_project.domain.entity.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailsViewModel(
    private val likedHistoryInteractor: LikedHistoryInteractor
) : ViewModel() {

    private val _favoriteState = MutableStateFlow<Boolean?>(null)
    val favoriteState: StateFlow<Boolean?> = _favoriteState.asStateFlow()

    suspend fun checkFavoriteStatus(courseId: Long) {
        _favoriteState.value = likedHistoryInteractor.isCourseInFavorite(courseId)
    }

    suspend fun toggleFavorite(course: Course) {
        val currentState = _favoriteState.value ?: false
        val newState = likedHistoryInteractor.invoke(course, currentState)
        _favoriteState.value = newState
    }
}