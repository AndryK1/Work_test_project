package com.practicum.work_test_project.ui.authorization.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {

    private val _email = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()

    private val _isFormValid = MutableStateFlow<Boolean>(false)
    val isFormValid: StateFlow<Boolean> = _isFormValid

    init {
        _email.value = ""
        _password.value = ""
        _isFormValid.value = false
    }

    fun onEmailChanged(email: String) {
        _email.value = email
        validateForm()
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
        validateForm()
    }

    private fun validateForm() {
        val email = _email.value ?: ""
        val password = _password.value ?: ""

        // Проверка email по маске "текст@текст.текст" и отсутствие кириллицы (ТЗ)
        val emailValid = isValidEmail(email)
        // Пароль не должен быть пустым (ТЗ)
        val passwordValid = password.isNotEmpty()

        _isFormValid.value = emailValid && passwordValid
    }

    private fun isValidEmail(email: String): Boolean {

        if (email.isEmpty()) return false

        val cyrillicPattern = Regex("[а-яА-Я]")
        if (cyrillicPattern.containsMatchIn(email)) return false

        val emailPattern = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        return emailPattern.matches(email)
    }
}