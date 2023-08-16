@file:Suppress("UNCHECKED_CAST")

package com.example.e_commerce.util

import com.example.e_commerce.data.local.entity.UserRegistrationEntity
import com.example.e_commerce.domain.models.Product
import com.example.e_commerce.domain.models.ProductReviews
import com.example.e_commerce.domain.models.UserOrder
import com.example.e_commerce.util.Constants.USER_ADDRESS
import com.example.e_commerce.util.Constants.USER_EMAIL
import com.example.e_commerce.util.Constants.USER_IMAGE
import com.example.e_commerce.util.Constants.USER_NAME
import com.example.e_commerce.util.Constants.USER_PASSWORD
import com.example.e_commerce.util.Constants.USER_PHONE

object HelperMethods {

    fun hashMapToProduct(hashMap: HashMap<*, *>): Product {
        return Product(
            id = hashMap["id"] as String,
            category = hashMap["pCategory"] as String,
            imagesUrl = hashMap["pImageUrl"] as String,
            name = hashMap["pName"] as String,
            numberOfItems = (hashMap["pNumOfItems"] as Long).toInt(),
            price = hashMap["pPrice"].toString(),
            rating = hashMap["pRating"] as String,
            description = hashMap["pDescription"] as String,
            uploadData = hashMap["pUploadDate"] as String,
            reviews = (hashMap["pReviews"] as? List<HashMap<*, *>>)?.map { hashMapToProductReview(it) }
                ?: emptyList(),
            availableSizes = hashMap["availableSizes"] as? List<String> ?: emptyList(),
        )
    }

    fun productToHashMap(product: Product): HashMap<String, Any> {
        val productMap: HashMap<String, Any> = HashMap()
        return productMap.apply {
            put("id", product.id)
            put("pName", product.name)
            put("pCategory", product.category)
            put("pImageUrl", product.imagesUrl)
            put("pDescription", product.description)
            put("pNumOfItems", product.numberOfItems)
            put("pPrice", product.price)
            put("pAvailableSizes", product.availableSizes)
            put("pRating", product.rating)
            put("pReviews", product.reviews)
            put("pUploadDate", product.uploadData)
        }
    }

    private fun hashMapToProductReview(hashMap: HashMap<*, *>): ProductReviews {
        return ProductReviews(
            userName = hashMap["userName"] as String,
            rating = (hashMap["rating"] as Long).toInt(),
            date = hashMap["date"] as String,
            comment = hashMap["comment"] as String,
        )
    }

    fun hashMapToUserRegistrationEntity(hashMap: HashMap<*, *>): UserRegistrationEntity {
        return UserRegistrationEntity(
            userName = hashMap[USER_NAME] as String,
            userAddress = hashMap[USER_ADDRESS] as String,
            userPassword = hashMap[USER_PASSWORD] as String,
            userImage = hashMap[USER_IMAGE] as String,
            userEmail = hashMap[USER_EMAIL] as String,
            userPhone = hashMap[USER_PHONE] as String,
        )
    }

    fun userRegistrationEntityToHashMap(user: UserRegistrationEntity): HashMap<String, Any> {
        return HashMap<String, Any>().apply {
            put(USER_NAME, user.userName)
            put(USER_IMAGE, user.userImage)
            put(USER_EMAIL, user.userEmail)
            put(USER_PASSWORD, user.userPassword)
            put(USER_ADDRESS, user.userAddress)
            put(USER_PHONE, user.userPhone)
        }
    }

    fun hashMapToUserOrder(hashMap: HashMap<*, *>): UserOrder {
        return UserOrder(
            productId = hashMap["productId"] as String,
            name = hashMap["name"] as String,
            date = hashMap["date"] as String,
            numOfRequestedItems = (hashMap["numOfRequestedItems"] as Long).toInt(),
            numOfAvailableItems = (hashMap["numOfAvailableItems"] as Long).toInt(),
            unitPrice = (hashMap["price"] as Long).toFloat(),
            image = hashMap["image"] as String,
        )
    }
}
