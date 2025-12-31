package com.practicum.work_test_project.ui.search.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.work_test_project.domain.api.CoursesRepositoryInteractor
import com.practicum.work_test_project.domain.db.LikedHistoryInteractor
import com.practicum.work_test_project.domain.entity.Course
import com.practicum.work_test_project.ui.search.SearchState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SearchViewModel(
    val interactor: CoursesRepositoryInteractor,
    val likedHistoryInteractor : LikedHistoryInteractor
) : ViewModel() {


    init {
        loadFavorites()
    }
    private var searchJob : Job? = null
    //На будущее (пока нет смысла передавать текст запроса дальше)
    private var savedQuery: String = ""
    private var lastSearchText: String? = null
    //
    //Последний state
    private var lastState: SearchState = SearchState.Content(emptyList())

    private val _state = MutableStateFlow<SearchState?>(SearchState.Empty)
    var state : StateFlow<SearchState?> = _state.asStateFlow()

    private val _favorites = MutableStateFlow<List<Course>>(emptyList())
    val favorites : StateFlow<List<Course>> = _favorites.asStateFlow()

    private var listOfCourses: ArrayList<Course> = ArrayList()

    fun searchDebounce(changedText: String) {
        if (lastSearchText == changedText) {
            return
        }

        this.lastSearchText = changedText

        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            loadFavorites()
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
                        renderState(SearchState.Loading)
                    }
                    .catch { e ->
                        renderState(SearchState.Error(
                            message = "Ошибка: ${e.message ?: "Неизвестная ошибка"}"
                        ))
                    }
                    .collect { (courses, error) ->
                        when {
                            error != null -> {
                                renderState(SearchState.Error(error))
                            }
                            courses != null && courses.isNotEmpty() -> {
                                val updatedCourses = updateCoursesWithFavorites(courses)

                                renderState(SearchState.Content(updatedCourses))
                            }
                            else -> {
                                renderState(SearchState.Empty)
                            }
                        }
                    }
            }
        }

    }

    private fun loadFavorites(){
        viewModelScope.launch {
            likedHistoryInteractor.getLikedCourses().collect { courses ->
                _favorites.value = courses

                updateCurrentList()
            }
        }
    }

    private fun updateCoursesWithFavorites(courses: List<Course>): List<Course> {

        val favoriteIds = _favorites.value.map { it.id }

        // Обновляем каждый курс: если его id есть в избранных, то isFavorite = true
        return courses.map { course ->

            val isFavorite = favoriteIds.contains(course.id)
            course.copy(isFavorite = isFavorite)
        }
    }

    suspend fun refreshFavorites() {
        try {
            val newFavorites = likedHistoryInteractor.getLikedCourses()
                .first()  // только первый элемент т.к. остальные никак поменяться не могли

            _favorites.value = newFavorites

            updateCurrentList()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun toggleFavorite(course: Course) {
        viewModelScope.launch {
            val isFavorite = likedHistoryInteractor.isCourseInFavorite(course.id)

            val newFavoriteStatus = likedHistoryInteractor.invoke(course, isFavorite)

            val currentFavorites = _favorites.value.toMutableList()
            if (newFavoriteStatus) {
                currentFavorites.add(course.copy(isFavorite = true))
            } else {
                currentFavorites.removeAll { it.id == course.id }
            }
            _favorites.value = currentFavorites

            updateCurrentList()
        }
    }

    // обновляет текущее состояние при изменении избранного
    private fun updateCurrentList(){
        val currentState = _state.value
        if (currentState is SearchState.Content) {
            val updatedCourses = updateCoursesWithFavorites(currentState.courses)
            _state.value = SearchState.Content(updatedCourses)
        }
    }

    private fun renderState(state: SearchState) {
        lastState = state
        _state.value = state
    }

    companion object{
        private const val SEARCH_DEBOUNCE_DELAY = 500L
    }
}