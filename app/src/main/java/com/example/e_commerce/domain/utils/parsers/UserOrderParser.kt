package com.example.e_commerce.domain.utils.parsers

import com.example.e_commerce.domain.models.UserOrder

object UserOrderParser {
    fun hashMapToUserOrder(hashMap: HashMap<*, *>): UserOrder {
        return UserOrder(
            productId = hashMap["productId"] as String,
            name = hashMap["name"] as String,
            date = hashMap["date"] as String,
            numOfRequestedItems = (hashMap["numOfRequestedItems"] as Long).toInt(),
            numOfAvailableItems = (hashMap["numOfAvailableItems"] as Long).toInt(),
            unitPrice = (hashMap["unitPrice"] as Long).toFloat(),
            image = hashMap["image"] as String,
            category = hashMap["category"] as String,
        )
    }
}
