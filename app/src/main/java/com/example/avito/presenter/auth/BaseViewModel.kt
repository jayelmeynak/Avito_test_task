package com.example.avito.presenter.auth

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    protected val _email = mutableStateOf("")
    val email: MutableState<String> = _email

    protected val _password = mutableStateOf("")
    val password: MutableState<String> = _password

    protected val _emailError = mutableStateOf(false)
    val emailError: MutableState<Boolean> = _emailError

    protected val _passwordError = mutableStateOf(false)
    val passwordError: MutableState<Boolean> = _passwordError

    protected val _error = mutableStateOf(false)
    val error: MutableState<Boolean> = _error

    protected val _errorMessage = mutableStateOf("")
    val errorMessage: MutableState<String> = _errorMessage

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun changeEmail(value: String) {
        _email.value = value
        _emailError.value = !isValidEmail(value)
        if(_emailError.value){
            _error.value = true
            _errorMessage.value = "Введите правильный Email"
        }else{
            _error.value = false
            _errorMessage.value = ""
        }
    }

    open fun changePassword(value: String) {
        _password.value = value
        _passwordError.value = value.length > 24 || value.length < 8
        if (_passwordError.value) {
            _error.value = true
            if (value.length > 24) {
                _errorMessage.value = "Пароль превышает длину в 24 символа"
            }
            if(value.length<8){
                _errorMessage.value = "Пароль должен содержать не менее 8 символов"
            }
        }
    }
}