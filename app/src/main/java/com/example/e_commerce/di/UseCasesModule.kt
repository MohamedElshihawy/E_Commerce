package com.example.e_commerce.di

import com.example.e_commerce.domain.useCases.AddNewProductUseCase
import com.example.e_commerce.domain.useCases.AddOrderToCartUseCase
import com.example.e_commerce.domain.useCases.AddProductImageUseCase
import com.example.e_commerce.domain.useCases.DeleteAllOrdersFromCartUserCase
import com.example.e_commerce.domain.useCases.DeleteOrderFromCartUseCase
import com.example.e_commerce.domain.useCases.GetAllOrdersInCartUseCase
import com.example.e_commerce.domain.useCases.GetAllProductsUseCase
import com.example.e_commerce.domain.useCases.GetCurrentUserUseCase
import com.example.e_commerce.domain.useCases.GetSingleProductUseCase
import com.example.e_commerce.domain.useCases.SignInUseCase
import com.example.e_commerce.domain.useCases.SignUpUseCase
import com.example.e_commerce.domain.useCases.SubmitUserOrdersUseCase
import com.example.e_commerce.domain.useCases.UpdateUserDataUseCase
import com.example.e_commerce.domain.useCases.UploadUserProfileImageUseCase
import com.example.e_commerce.domain.useCases.UploadUserReviewUseCase
import com.example.e_commerce.domain.useCases.UseCasesWrapper
import org.koin.dsl.module

val userCasesModule = module {

    includes(repositoriesModule)

    single {
        SignInUseCase(get())
    }

    single {
        SignUpUseCase(get())
    }
    single {
        AddProductImageUseCase(get())
    }
    single {
        AddNewProductUseCase(get())
    }

    single {
        GetAllProductsUseCase(get())
    }

    single {
        UpdateUserDataUseCase(get())
    }

    single {
        GetCurrentUserUseCase(get())
    }

    single {
        UploadUserProfileImageUseCase(get())
    }

    single {
        GetSingleProductUseCase(get())
    }

    single {
        UploadUserReviewUseCase(get())
    }

    single {
        AddOrderToCartUseCase(get())
    }

    single {
        DeleteOrderFromCartUseCase(get())
    }

    single {
        DeleteAllOrdersFromCartUserCase(get())
    }

    single {
        SubmitUserOrdersUseCase(get())
    }

    single {
        GetAllOrdersInCartUseCase(get())
    }

    single {
        UseCasesWrapper(
            get(), get(),
            get(), get(),
            get(), get(),
            get(), get(),
            get(), get(),
            get(), get(),
            get(), get(),
            get(),
        )
    }
}
