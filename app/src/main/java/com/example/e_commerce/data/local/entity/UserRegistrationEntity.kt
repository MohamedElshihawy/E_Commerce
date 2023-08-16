package com.example.e_commerce.data.local.entity

import com.example.e_commerce.domain.models.UserRegistration
import com.example.e_commerce.presentation.common.state.CustomTextFieldState

data class UserRegistrationEntity(
    val userName: String = "",
    val userEmail: String = "",
    val userPhone: String = "",
    val userPassword: String = "",
    val userAddress: String = "",
    val userImage: String = "",
) {

    fun toUserRegistration(): UserRegistration {
        return UserRegistration(
            userEmailField = CustomTextFieldState(value = userEmail),
            userPasswordField = CustomTextFieldState(value = userPassword),
            userNameField = CustomTextFieldState(value = userName),
            userPhoneField = CustomTextFieldState(value = userPhone),
        )
    }
}
