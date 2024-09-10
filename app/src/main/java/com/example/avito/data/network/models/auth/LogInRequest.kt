package com.example.avito.data.network.models.auth

data class LogInRequest(
    val name: String,
    val email: String,
    val password: String,
    val cpassword: String,
)