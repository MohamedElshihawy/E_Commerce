package com.example.e_commerce.presentation.features.userCart.manageOrder

data class SingleOrderState(
    val id: String,
    var count: Int = 1,
    val isSelected: Boolean = false,
) {
    fun increaseCount() {
        count++
    }

    fun decreaseValue() {
        if (count > 1) {
            count--
        }
    }
}
