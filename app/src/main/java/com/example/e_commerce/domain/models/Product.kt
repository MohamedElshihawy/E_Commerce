package com.example.e_commerce.domain.models

import com.example.e_commerce.presentation.common.state.CustomTextFieldState
import com.example.e_commerce.presentation.features.admin.addNewProduct.AddNewProductState

data class Product(
    val id: String,
    val category: String = "",
    val imagesUrl: String = "",
    val name: String = "",
    val description: String = "",
    val numberOfItems: Int = 0,
    val price: String = "",
    val availableSizes: List<String> = emptyList(),
    val reviews: List<ProductReviews?> = emptyList(),
    val rating: String = "0",
    val uploadData: String = "",
) {


    fun toProductState(): AddNewProductState {
        return AddNewProductState(
            category = category,
            name = CustomTextFieldState(value = name),
            price = CustomTextFieldState(value = price),
            description = CustomTextFieldState(value = description),
            availableSizes = availableSizes.toMutableList(),
            imageUrl = imagesUrl,
        )
    }
}
