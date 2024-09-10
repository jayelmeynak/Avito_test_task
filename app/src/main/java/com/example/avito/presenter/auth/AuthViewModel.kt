package com.example.avito.presenter.auth

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.avito.data.network.models.auth.LogInRequest
import com.example.avito.data.network.models.auth.SignInRequest
import com.example.avito.data.repository.UserRepository
import com.example.avito.shared.TokenManager
import kotlinx.coroutines.launch

class AuthViewModel(application: Application): BaseViewModel(application) {
    private val userRepository = UserRepository()

    private val tokenManager = TokenManager(application)

    private val _name = mutableStateOf("")
    val name: MutableState<String> = _name

    private val _cPassword = mutableStateOf("")
    val cPassword: MutableState<String> = _cPassword

    private val _cPasswordError = mutableStateOf(false)
    val cPasswordError: MutableState<Boolean> = _cPasswordError

    fun onNameChange(value: String) {
        _name.value = value
    }

    override fun changePassword(value: String) {
        _password.value = value
        _passwordError.value = value.length > 24 || value.length < 8
        _cPasswordError.value = _cPassword.value != value
    }

    fun onCPasswordChange(value: String) {
        _cPassword.value = value
        _cPasswordError.value = _password.value != value
    }

    fun signIn(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val request = SignInRequest(
                email = email.value,
                password = password.value,
            )
            val result = userRepository.signInUser(request)
            if (result.isSuccess) {
                tokenManager.saveToken((result.getOrNull()))
                onSuccess()
            } else {
                onError(result.exceptionOrNull()?.message ?: "Exception")
            }
        }
    }

    fun getToken() = tokenManager.getToken()

    fun login(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val request = LogInRequest(
                name = name.value,
                email = email.value,
                password = password.value,
                cpassword = cPassword.value
            )

            val result = userRepository.logInUser(request)
            if (result.isSuccess) {
                onSuccess()
            } else {
                onError(result.exceptionOrNull()?.message ?: "Exception")
            }
        }
    }

}