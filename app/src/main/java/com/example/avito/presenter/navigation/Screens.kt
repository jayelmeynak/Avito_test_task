package com.example.avito.presenter.navigation

sealed class Screens(val route: String) {
    object LoginScreen : Screens("login")
    object SignInScreen : Screens("signIn")
    object ProductListScreen: Screens("productList")
    object ProductScreen: Screens("product")
}