package com.example.avito.data.auth.models

data class LogInRequest(
    val username: String,
    val password: String,
    val cPassword: String,
    val email: String,
)