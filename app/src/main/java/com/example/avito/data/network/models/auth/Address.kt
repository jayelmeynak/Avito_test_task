package com.example.avito.data.network.models.auth

data class Address(
    val city: String,
    val country: String,
    val postalCode: String,
    val state: String,
    val street: String
)