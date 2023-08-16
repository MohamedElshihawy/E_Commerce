package com.example.e_commerce.domain.models

data class ProductReviews(
    val rating: Int = 0,
    val comment: String = "",
    val userName: String = "",
    val date: String,
)
