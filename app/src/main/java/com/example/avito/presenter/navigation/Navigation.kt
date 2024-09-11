package com.example.avito.presenter.navigation

import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.avito.presenter.auth.AuthViewModel
import com.example.avito.presenter.auth.LoginScreen
import com.example.avito.presenter.auth.SignInScreen
import com.example.avito.presenter.product.ProductScreen

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
        startDestination = Screens.LoginScreen.route,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(500)
            )
        }
    ) {
        composable(Screens.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(Screens.SignInScreen.route) {
            SignInScreen(navController)
        }
        composable(
            route = Screens.ProductScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString("id")
            if (id != null) {
                ProductScreen(id = id, navController = navController)
            }
        }

    }
}