package com.practicum.work_test_project.ui.search

import com.practicum.work_test_project.domain.entity.Course

sealed interface SearchState {

    object Loading : SearchState

    object Empty : SearchState

    data class Content(val courses : List<Course>) : SearchState

    data class Error(val message : String) : SearchState
}