package com.example.e_commerce.presentation.signIn

import com.example.e_commerce.presentation.common.state.CustomTextFieldState

data class SignInScreenState(
    val userEmailField: CustomTextFieldState = CustomTextFieldState(),
    val userPasswordField: CustomTextFieldState = CustomTextFieldState(),
    val checkBox: Boolean = false,
)
