package com.example.e_commerce.di

import com.example.e_commerce.data.remote.repository.AuthenticationRepositoryImpl
import com.example.e_commerce.domain.repository.AuthenticationRepository
import com.example.e_commerce.domain.useCases.SignInUseCase
import com.example.e_commerce.domain.useCases.SignUpUseCase
import com.example.e_commerce.domain.useCases.UseCasesWrapper
import com.example.e_commerce.presentation.signIn.SignInViewModel
import com.example.e_commerce.presentation.signUp.SignUpViewModel
import com.example.e_commerce.presentation.splash.SplashViewModel
import com.example.e_commerce.util.Constants.ROOT
import com.google.firebase.database.FirebaseDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        FirebaseDatabase.getInstance()
    }
    single {
        get<FirebaseDatabase>().getReference(ROOT)
    }

    single {
        SignInUseCase(get())
    }

    single {
        SignUpUseCase(get())
    }

    single {
        UseCasesWrapper(get(), get())
    }

    single<AuthenticationRepository> {
        AuthenticationRepositoryImpl(get())
    }
    viewModel {
        SignUpViewModel(get())
    }

    viewModel {
        SignInViewModel(get(), androidApplication())
    }
    viewModel {
        SplashViewModel(get(), androidApplication())
    }
}
