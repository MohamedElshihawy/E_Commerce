package com.example.e_commerce.presentation.common.state

data class CustomTextFieldState(
    val value: String = "",
    var showError: Boolean = false,
    var errorMessage: String = "",
)
