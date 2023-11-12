package com.example.e_commerce.domain.models

import com.example.e_commerce.util.Constants.WAITING

data class RequestToAdmin(
    val orders: List<UserOrder> = emptyList(),
    val userName: String,
    val city: String,
    val userImage: String,
    val address: String,
    val phoneNumber: String,
    val date: String,
    val status: String = WAITING,
)
