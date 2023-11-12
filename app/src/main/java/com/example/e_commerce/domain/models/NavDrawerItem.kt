package com.example.e_commerce.domain.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material.icons.filled.RequestPage
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.e_commerce.navigatoin.Screen

data class BottomNavItem(
    val id: Int,
    val icon: ImageVector,
    val destination: String,
    val contentDescription: String = "",
)

val userNavItems = listOf(

    BottomNavItem(
        id = 0,
        destination = Screen.UserHomeScreen.route,
        icon = Icons.Default.Home,
    ),
    BottomNavItem(
        id = 0,
        destination = Screen.UserCartScreen.route,
        icon = Icons.Default.ShoppingCart,
    ),
    BottomNavItem(
        id = 3,
        destination = Screen.UserProfileScreen.route,
        icon = Icons.Default.ManageAccounts,
    ),
)

val adminNavItems = listOf(
    BottomNavItem(
        id = 0,
        destination = "admin home",
        icon = Icons.Default.ModeEdit,
    ),
    // add new product
    BottomNavItem(
        id = 1,
        destination = Screen.AdminCategoriesManagement.route,
        icon = Icons.Default.AddBusiness,
    ),
    BottomNavItem(
        id = 2,
        destination = Screen.AdminOrdersCartScreen.route,
        icon = Icons.Default.RequestPage,
    ),
)
