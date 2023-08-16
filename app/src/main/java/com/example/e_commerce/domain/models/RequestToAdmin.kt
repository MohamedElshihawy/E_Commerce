package com.example.e_commerce.domain.models

data class RequestToAdmin(
    val orders: List<UserOrder> = emptyList(),
    val userName: String,
    val city: String,
    val address: String,
    val phoneNumber: String,
    val date: String,
    val status: String = "Not Shipped",
)
