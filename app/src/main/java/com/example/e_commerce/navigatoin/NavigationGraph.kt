package com.example.e_commerce.navigatoin

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.e_commerce.presentation.signIn.SignInScreen
import com.example.e_commerce.presentation.signUp.SignUpScreen
import com.example.e_commerce.presentation.splash.SplashScreen

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(route = Screen.SignUp.route) {
            SignUpScreen(navController = navController)
        }

        composable(route = Screen.SignIn.route) {
            SignInScreen()
        }
    }
}
