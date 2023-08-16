package com.example.e_commerce.domain.models

import com.example.e_commerce.R

data class CategoryItemData(
    val name: String,
    val icon: Int,
    val contentDescription: String,
)

val categoriesItemsList = listOf(
    CategoryItemData(
        name = "Cloths",
        icon = R.drawable.icon_clothes,
        contentDescription = "Clothes",
    ),
    CategoryItemData(
        name = "Food",
        icon = R.drawable.icon_food,
        contentDescription = "Food",
    ),
    CategoryItemData(
        name = "Grocery",
        icon = R.drawable.icon_grocery,
        contentDescription = "Grocery",
    ),
    CategoryItemData(
        name = "HeadSets",
        icon = R.drawable.icon_headset,
        contentDescription = "HeadSets",
    ),
    CategoryItemData(
        name = "Home Decoration",
        icon = R.drawable.icon_home_decorations,
        contentDescription = "Decoration",
    ),
    CategoryItemData(
        name = "Jewelry",
        icon = R.drawable.icon_jewelry,
        contentDescription = "Jewelry",
    ),
    CategoryItemData(
        name = "Laptops",
        icon = R.drawable.icon_laptop,
        contentDescription = "Laptops",
    ),
    CategoryItemData(
        name = "Mobiles",
        icon = R.drawable.icon_mobile,
        contentDescription = "Mobiles",
    ),
    CategoryItemData(
        name = "Souvenirs",
        icon = R.drawable.icon_souvenirs,
        contentDescription = "Souvenirs",
    ),
    CategoryItemData(
        name = "Stationery",
        icon = R.drawable.icon_stationery,
        contentDescription = "Stationery",
    ),
)
