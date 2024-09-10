package com.example.avito.presenter.auth

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel

open class BaseViewModel(application: Application): AndroidViewModel(application) {
    protected val _email = mutableStateOf("")
    val email: MutableState<String> = _email

    protected val _password = mutableStateOf("")
    val password: MutableState<String> = _password

    protected val _emailError = mutableStateOf(false)
    val emailError: MutableState<Boolean> = _emailError

    protected val _passwordError = mutableStateOf(false)
    val passwordError: MutableState<Boolean> = _passwordError


    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun changeEmail(value: String) {
        _email.value = value
        _emailError.value = !isValidEmail(value)
    }

    open fun changePassword(value: String) {
        _password.value = value
        _passwordError.value = value.length > 24 || value.length < 8
    }
}