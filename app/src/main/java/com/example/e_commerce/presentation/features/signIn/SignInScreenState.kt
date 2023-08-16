package com.example.e_commerce.presentation.features.signIn

import com.example.e_commerce.presentation.common.state.CustomTextFieldState

data class SignInScreenState(
    val userNameField: CustomTextFieldState = CustomTextFieldState(),
    val userPasswordField: CustomTextFieldState = CustomTextFieldState(),
    val checkBox: Boolean = false,
)
