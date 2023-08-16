package com.example.e_commerce.domain.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

data class NavDrawerItem(
    val id: Int,
    val text: String,
    val icon: ImageVector,
    val contentDescription: String = "",
)

val navDrawerItems = listOf(
    NavDrawerItem(
        id = 0,
        text = "Cart",
        icon = Icons.Default.ShoppingCart,
    ),
    NavDrawerItem(
        id = 0,
        text = "Orders",
        icon = Icons.Default.Checklist,
    ),
    NavDrawerItem(
        id = 0,
        text = "Categories",
        icon = Icons.Default.Category,
    ),
    NavDrawerItem(
        id = 0,
        text = "Manage Profile",
        icon = Icons.Default.ManageAccounts,
    ),
    NavDrawerItem(
        id = 0,
        text = "Log out",
        icon = Icons.Default.Logout,
    ),
)
