package com.example.e_commerce.di

import com.example.e_commerce.data.local.dataStore.PreferenceDataStoreHelper
import com.example.e_commerce.navigatoin.NavigationViewModel
import com.example.e_commerce.presentation.features.admin.addNewProduct.AddNewProductViewModel
import com.example.e_commerce.presentation.features.admin.ordersCart.AdminCartViewModel
import com.example.e_commerce.presentation.features.shared.signIn.SignInViewModel
import com.example.e_commerce.presentation.features.shared.signUp.SignUpViewModel
import com.example.e_commerce.presentation.features.shared.splash.SplashViewModel
import com.example.e_commerce.presentation.features.user.manageUserProfile.ManageUserProfileViewModel
import com.example.e_commerce.presentation.features.user.productDetails.ProductDetailsViewModel
import com.example.e_commerce.presentation.features.user.userCart.confirmOrder.ConfirmOrderViewModel
import com.example.e_commerce.presentation.features.user.userCart.manageOrder.CartViewModel
import com.example.e_commerce.presentation.features.user.userHomePage.UserHomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    includes(useCasesModule)

    single {
        PreferenceDataStoreHelper(androidContext())
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

    viewModel {
        AddNewProductViewModel(get())
    }
    viewModel {
        UserHomeViewModel(get() , get())
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
        ConfirmOrderViewModel(get(), androidApplication())
    }

    viewModel {
        AdminCartViewModel(get(), androidApplication())
    }

    viewModel {
        NavigationViewModel(androidApplication())
    }
}
