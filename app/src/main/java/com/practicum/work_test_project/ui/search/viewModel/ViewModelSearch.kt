package com.practicum.work_test_project.ui.search.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.work_test_project.domain.api.CoursesRepositoryInteractor
import com.practicum.work_test_project.domain.entity.Course
import com.practicum.work_test_project.ui.search.SearchState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ViewModelSearch(
    val interactor: CoursesRepositoryInteractor
) : ViewModel() {


    private var searchJob : Job? = null
    //На будущее (пока нет смысла передавать текст запроса дальше)
    private var savedQuery: String = ""
    private var lastSearchText: String? = null
    //
    //Последний state
    private var lastState: SearchState = SearchState.Content(emptyList())

    private val _state = MutableStateFlow<SearchState?>(SearchState.Empty)
    var state : StateFlow<SearchState?> = _state.asStateFlow()

    private var listOfCourses: ArrayList<Course> = ArrayList()

    fun searchDebounce(changedText: String) {
        if (lastSearchText == changedText) {
            return
        }

        this.lastSearchText = changedText

        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            search(changedText)
        }
    }

    private fun search(changedText: String) {
        savedQuery = changedText

        viewModelScope.launch {
            if (changedText.isEmpty()) {
                listOfCourses.clear()
                _state.value = SearchState.Empty

            } else {
                interactor.searchCourses()
                    .onStart {
                        // Показываем загрузку
                        _state.value = SearchState.Loading
                    }
                    .catch { e ->
                        // Обработка ошибок
                        _state.value = SearchState.Error(
                            message = "Ошибка: ${e.message ?: "Неизвестная ошибка"}"
                        )
                    }
                    .collect { (courses, error) ->
                        when {
                            error != null -> {
                                _state.value = SearchState.Error(error)
                            }
                            courses != null && courses.isNotEmpty() -> {
                                _state.value = SearchState.Content(courses)
                            }
                            else -> {
                                _state.value = SearchState.Empty
                            }
                        }
                    }
            }
        }

    }

    fun clearSearch() {
        searchJob?.cancel()
        _state.value = SearchState.Empty
    }

    private fun renderState(state: SearchState) {
        lastState = state
        _state.value = state
    }

    companion object{
        private const val SEARCH_DEBOUNCE_DELAY = 500L
    }
}