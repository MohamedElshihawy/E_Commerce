package com.example.e_commerce.navigatoin

sealed class Screen(val route: String) {

    object Splash : Screen(route = "Splash_Screen")
}
