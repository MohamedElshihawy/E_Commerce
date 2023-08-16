package com.example.e_commerce.domain.models

data class UserOrder(
    val productId: String,
    val name: String,
    val numOfRequestedItems: Int = 1,
    val numOfAvailableItems: Int,
    val image: String,
    val unitPrice: Float,
    val totalPrice: Float = 0f,
    val date: String,
)
