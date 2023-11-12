package com.example.e_commerce.domain.utils.parsers

import com.example.e_commerce.domain.models.Product
import com.example.e_commerce.domain.models.ProductReviews

object ProductParser {
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
            reviews = (hashMap["pReviews"] as? List<HashMap<*, *>>)?.map {
                hashMapToProductReview(
                    it,
                )
            }
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
}
