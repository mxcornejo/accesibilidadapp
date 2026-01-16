package com.example.accesibilidadapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.accesibilidadapp.ui.screens.LoginScreen
import com.example.accesibilidadapp.ui.screens.RecoverPasswordScreen
import com.example.accesibilidadapp.ui.screens.RegisterScreen
import com.example.accesibilidadapp.ui.screens.SplashScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Recover : Screen("recover")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        composable(Screen.Splash.route) {
            SplashScreen(
                onStartClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onRegisterClick = { navController.navigate(Screen.Register.route) },
                onRecoverClick = { navController.navigate(Screen.Recover.route) }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onLoginClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onRegisterSubmit = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Recover.route) {
            RecoverPasswordScreen(
                onSendClick = {
                },
                onBackToLoginClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Recover.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
