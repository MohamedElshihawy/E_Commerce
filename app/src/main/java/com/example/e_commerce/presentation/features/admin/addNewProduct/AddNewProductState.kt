package com.example.e_commerce.presentation.features.admin.addNewProduct

import com.example.e_commerce.domain.models.Product
import com.example.e_commerce.presentation.common.state.CustomTextFieldState

data class AddNewProductState(
    val category: String = "",
    val imageUrl: String = "",
    val name: CustomTextFieldState = CustomTextFieldState(),
    val description: CustomTextFieldState = CustomTextFieldState(),
    val price: CustomTextFieldState = CustomTextFieldState(),
    val numberOfItems: CustomTextFieldState = CustomTextFieldState(),
    val availableSizes: MutableList<String> = mutableListOf(),
) {
    fun toProductModel(): Product {
        return Product(
            id = "",
            category = category,
            name = name.value,
            description = description.value,
            price = price.value,
            availableSizes = availableSizes,
            imagesUrl = imageUrl,
            numberOfItems = numberOfItems.value.toInt(),
        )
    }
}
