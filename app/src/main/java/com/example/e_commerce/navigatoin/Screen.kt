package com.example.e_commerce.navigatoin

sealed class Screen(val route: String) {

    object Splash : Screen(route = "Splash")
    object SignUp : Screen(route = "Sign_Up")
    object SignIn : Screen(route = "Sign_In")
}
