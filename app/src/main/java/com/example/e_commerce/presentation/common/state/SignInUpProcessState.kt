package com.example.e_commerce.presentation.common.state

data class SignInUpProcessState(
    var isLoading: Boolean = false,
    var isSuccess: String = "",
    var isError: String = "",
)
