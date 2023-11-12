package com.example.e_commerce.domain.models

data class UserOrder(
    val category: String,
    val productId: String,
    val name: String,
    val numOfRequestedItems: Int = 1,
    val numOfAvailableItems: Int,
    val image: String,
    val unitPrice: Float,
    val totalPrice: Float = unitPrice * numOfRequestedItems,
    val date: String,
)
