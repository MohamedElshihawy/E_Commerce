package com.example.e_commerce.presentation.signIn

sealed class SignInEvents {

    data class EnteredEmail(val email: String) : SignInEvents()
    data class EnteredPassword(val password: String) : SignInEvents()
    data class SignIn(val name: String, val password: String) : SignInEvents()
    data class RememberMe(val checkBox: Boolean) : SignInEvents()
}
