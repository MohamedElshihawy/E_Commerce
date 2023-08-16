package com.example.e_commerce.di

import com.example.e_commerce.presentation.features.addNewProduct.AddNewProductViewModel
import com.example.e_commerce.presentation.features.manageUserProfile.ManageUserProfileViewModel
import com.example.e_commerce.presentation.features.productDetails.ProductDetailsViewModel
import com.example.e_commerce.presentation.features.signIn.SignInViewModel
import com.example.e_commerce.presentation.features.signUp.SignUpViewModel
import com.example.e_commerce.presentation.features.splash.SplashViewModel
import com.example.e_commerce.presentation.features.userCart.confirmOrder.ConfirmOrderViewModel
import com.example.e_commerce.presentation.features.userCart.manageOrder.CartViewModel
import com.example.e_commerce.presentation.features.userHomePage.UserHomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    includes(userCasesModule)

    viewModel {
        SignUpViewModel(get())
    }

    viewModel {
        SignInViewModel(get(), androidApplication())
    }
    viewModel {
        SplashViewModel(get(), androidApplication())
    }

    viewModel {
        AddNewProductViewModel(get())
    }
    viewModel {
        UserHomeViewModel(get())
    }

    viewModel {
        ManageUserProfileViewModel(get(), get())
    }

    viewModel {
        ProductDetailsViewModel(get(), androidApplication())
    }

    viewModel {
        CartViewModel(get(), androidApplication())
    }

    viewModel {
        ConfirmOrderViewModel(get() , androidApplication())
    }
}
