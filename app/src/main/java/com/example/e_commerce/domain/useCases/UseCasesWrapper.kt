package com.example.e_commerce.domain.useCases

data class UseCasesWrapper(
    val signInUseCase: SignInUseCase,
    val signUpUseCase: SignUpUseCase,
)
