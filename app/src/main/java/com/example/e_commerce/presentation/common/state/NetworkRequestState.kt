package com.example.e_commerce.presentation.common.state

data class NetworkRequestState(
    var isLoading: Boolean = false,
    var isSuccess: String = "",
    var isError: String = "",
)
