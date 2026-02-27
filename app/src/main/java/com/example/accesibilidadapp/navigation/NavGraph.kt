package com.example.accesibilidadapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.accesibilidadapp.data.AuthRepository
import kotlinx.coroutines.flow.first
import com.example.accesibilidadapp.ui.screens.HomeScreen
import com.example.accesibilidadapp.ui.screens.LoginScreen
import com.example.accesibilidadapp.ui.screens.RecoverPasswordScreen
import com.example.accesibilidadapp.ui.screens.RegisterScreen
import com.example.accesibilidadapp.ui.screens.SplashScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Recover : Screen("recover")
    object Home : Screen("home")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        composable(Screen.Splash.route) {
            // Espera a que Firebase restaure la sesión y navega automáticamente
            LaunchedEffect(Unit) {
                val user = AuthRepository.currentUserFlow.first()
                if (user != null) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            }
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
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
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

        composable(Screen.Home.route) {
            HomeScreen(
                onLogoutClick = {
                    AuthRepository.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
