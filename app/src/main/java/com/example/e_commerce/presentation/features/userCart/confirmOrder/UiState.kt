package com.example.e_commerce.presentation.features.userCart.confirmOrder

data class UiState(
    val name: String = "",
    val phoneNumber: String = "",
    val city: String = "",
    val address: String = "",
    val isNameError: Boolean = false,
    val isPhoneNumberError: Boolean = false,
    val isAddressError: Boolean = false,
    val isCityError: Boolean = false,
)
