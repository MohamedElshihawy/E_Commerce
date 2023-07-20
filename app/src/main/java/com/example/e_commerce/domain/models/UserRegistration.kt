package com.example.e_commerce.domain.models

import com.example.e_commerce.data.local.entity.UserRegistrationEntity
import com.example.e_commerce.presentation.common.state.CustomTextFieldState

data class UserRegistration(
    val userNameField: CustomTextFieldState = CustomTextFieldState(),
    val userEmailField: CustomTextFieldState = CustomTextFieldState(),
    val userPhoneField: CustomTextFieldState = CustomTextFieldState(),
    val userPasswordField: CustomTextFieldState = CustomTextFieldState(),
) {
    fun toUserRegistrationEntity(): UserRegistrationEntity {
        return UserRegistrationEntity(
            userName = userNameField.value,
            userEmail = userEmailField.value,
            userPhone = userPhoneField.value,
            userPassword = userPasswordField.value,
        )
    }
}
