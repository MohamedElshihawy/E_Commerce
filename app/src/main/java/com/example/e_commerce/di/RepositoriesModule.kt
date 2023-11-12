package com.example.e_commerce.di

import com.example.e_commerce.data.remote.repository.AdminCartOperationsRepositoryImpl
import com.example.e_commerce.data.remote.repository.AuthenticationRepositoryImpl
import com.example.e_commerce.data.remote.repository.ProductOperationsRepositoryImpl
import com.example.e_commerce.data.remote.repository.UserCartOperationsRepositoryImpl
import com.example.e_commerce.data.remote.repository.UserOperationRepositoryImpl
import com.example.e_commerce.domain.repository.AdminCartOperationsRepository
import com.example.e_commerce.domain.repository.AuthenticationRepository
import com.example.e_commerce.domain.repository.ProductOperationsRepository
import com.example.e_commerce.domain.repository.UserCartOperationsRepository
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

    single<UserCartOperationsRepository> {
        UserCartOperationsRepositoryImpl(get())
    }

    single<AdminCartOperationsRepository> {
        AdminCartOperationsRepositoryImpl(get())
    }
}
