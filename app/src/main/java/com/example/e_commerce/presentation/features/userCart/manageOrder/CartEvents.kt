package com.example.e_commerce.presentation.features.userCart.manageOrder

sealed class CartEvents {

    object CalculateTotalPrice : CartEvents()

    class IncreaseCounter(val counterId: String) : CartEvents()
    class DecreaseCounter(val counterId: String) : CartEvents()
    class SelectSingleItem(val itemId: String) : CartEvents()
    object SelectAllItems : CartEvents()
    class DeleteSingleItem(val itemId: String) : CartEvents()
    object DeleteAllItems : CartEvents()

    class CreateNewCounter(val counterId: String) : CartEvents()
}
