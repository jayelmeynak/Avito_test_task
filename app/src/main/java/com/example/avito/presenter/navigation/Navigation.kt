package com.example.avito.presenter.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.avito.presenter.auth.AuthViewModel
import com.example.avito.presenter.auth.LoginScreen
import com.example.avito.presenter.auth.SignInScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel(LocalContext.current as ComponentActivity)

    LaunchedEffect(authViewModel.getToken()) {
        if (authViewModel.getToken() != null) {
            navController.navigate(Screens.SignInScreen.route) {
                popUpTo(0)
            }
        } else {
            navController.navigate(Screens.LoginScreen.route) {
                popUpTo(0)
            }
        }
    }
    NavHost(
        navController = navController,
        startDestination = Screens.LoginScreen.route
    ) {
        composable(Screens.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(Screens.SignInScreen.route) {
            SignInScreen(navController)
        }
    }
}