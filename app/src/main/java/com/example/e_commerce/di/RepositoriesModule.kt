package com.example.e_commerce.di

import com.example.e_commerce.data.remote.repository.AuthenticationRepositoryImpl
import com.example.e_commerce.data.remote.repository.CartOperationsRepositoryImpl
import com.example.e_commerce.data.remote.repository.ProductOperationsRepositoryImpl
import com.example.e_commerce.data.remote.repository.UserOperationRepositoryImpl
import com.example.e_commerce.domain.repository.AuthenticationRepository
import com.example.e_commerce.domain.repository.CartOperationsRepository
import com.example.e_commerce.domain.repository.ProductOperationsRepository
import com.example.e_commerce.domain.repository.UserOperationRepository
import org.koin.dsl.module

val repositoriesModule = module {

    includes(dbModule)

    single<AuthenticationRepository> {
        AuthenticationRepositoryImpl(get())
    }

    single<ProductOperationsRepository> {
        ProductOperationsRepositoryImpl(get(), get())
    }

    single<UserOperationRepository> {
        UserOperationRepositoryImpl(get(), get())
    }

    single<CartOperationsRepository> {
        CartOperationsRepositoryImpl(get())
    }
}
