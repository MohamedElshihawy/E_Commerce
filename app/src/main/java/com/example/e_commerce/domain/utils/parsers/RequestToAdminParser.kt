@file:Suppress("UNCHECKED_CAST")

package com.example.e_commerce.domain.utils.parsers

import com.example.e_commerce.domain.models.RequestToAdmin
import com.example.e_commerce.domain.utils.parsers.UserOrderParser.hashMapToUserOrder

object RequestToAdminParser {

    fun hashMapToRequestToAdmin(hashMap: HashMap<*, *>): RequestToAdmin {
        return RequestToAdmin(
            userImage = hashMap["userImage"] as String,
            userName = hashMap["userName"] as String,
            city = hashMap["city"] as String,
            address = hashMap["address"] as String,
            date = hashMap["date"] as String,
            phoneNumber = hashMap["phoneNumber"] as String,
            orders = (hashMap["orders"] as List<HashMap<*, *>>).map {
                hashMapToUserOrder(it)
            },
        )
    }
}
