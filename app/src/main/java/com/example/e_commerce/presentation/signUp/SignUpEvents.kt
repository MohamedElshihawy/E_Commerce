package com.example.e_commerce.presentation.signUp

import com.example.e_commerce.domain.models.UserRegistration

sealed class SignUpEvents {

    data class SignUp(val userRegistration: UserRegistration) : SignUpEvents()

    data class EnteredUserName(val userName: String) : SignUpEvents()

    data class EnteredEmail(val email: String) : SignUpEvents()

    data class EnteredPhoneNumber(val phoneNumber: String) : SignUpEvents()

    data class EnteredPassword(val password: String) : SignUpEvents()
}
