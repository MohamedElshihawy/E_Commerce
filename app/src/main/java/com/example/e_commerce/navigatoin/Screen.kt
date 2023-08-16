package com.example.e_commerce.navigatoin

sealed class Screen(val route: String) {

    object Splash : Screen(route = "Splash")
    object SignUp : Screen(route = "Sign_Up")
    object SignIn : Screen(route = "Sign_In")
    object AdminCategoriesManagement : Screen(route = "admin_categories_management")
    object AdminAddsNewProduct : Screen(route = "admin_adds_new_product")
    object UserHomeScreen : Screen(route = "user_home_screen")
    object UserProfileScreen : Screen(route = "user_profile_screen")
    object ProductDetails : Screen(route = "product_details_screen")
    object CartScreen : Screen(route = "cart_screen")
    object ConfirmOrderCartScreen : Screen(route = "confirm_order_cart_screen")

    fun addArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { argument ->
                append("/$argument")
            }
        }
    }
}
