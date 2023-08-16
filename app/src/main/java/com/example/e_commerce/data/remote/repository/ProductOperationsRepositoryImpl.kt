package com.example.e_commerce.data.remote.repository

import android.net.Uri
import android.util.Log
import com.example.e_commerce.domain.models.Product
import com.example.e_commerce.domain.models.ProductReviews
import com.example.e_commerce.domain.repository.ProductOperationsRepository
import com.example.e_commerce.domain.utils.uploadImage
import com.example.e_commerce.util.Constants
import com.example.e_commerce.util.Constants.PRODUCTS_NODE
import com.example.e_commerce.util.Constants.ROOT
import com.example.e_commerce.util.HelperMethods.hashMapToProduct
import com.example.e_commerce.util.HelperMethods.productToHashMap
import com.example.e_commerce.util.Resource
import com.example.e_commerce.util.TimeDateFormatting
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ProductOperationsRepositoryImpl(
    private val firebaseDateBase: FirebaseDatabase,
    private val firebaseStorage: FirebaseStorage,
) : ProductOperationsRepository {

    override fun addNewProduct(product: Product): Flow<Resource<Boolean>> {
        return callbackFlow {
            trySend(Resource.Loading())
            val reference = firebaseDateBase.reference.child(ROOT).child(PRODUCTS_NODE).child(
                TimeDateFormatting.formattedTimeAndDate,
            )
            val productMap = productToHashMap(product)

            reference.updateChildren(productMap)
                .addOnCompleteListener { task ->
                    if (task.isComplete) {
                        if (task.isSuccessful) {
                            trySend(Resource.Success(task.isSuccessful))
                        } else {
                            trySend(
                                Resource.Error(
                                    message = task.exception?.message ?: "Unknown error ",
                                ),
                            )
                        }
                    }
                }

            awaitClose {
            }
        }
    }

    override fun uploadProductImage(uri: Uri): Flow<Resource<Uri?>> {
        val randomProductKey =
            TimeDateFormatting.formatCurrentDateAndTime(System.currentTimeMillis())

        val reference = firebaseStorage.reference.child(Constants.PRODUCTS_IMAGES)
            .child(randomProductKey)

        return uploadImage(reference, uri)
    }

    override suspend fun getAllProducts(): Flow<Resource<List<Product>>> {
        val reference = firebaseDateBase.reference.child(ROOT).child(PRODUCTS_NODE)

        return callbackFlow {
            trySend(Resource.Loading())

            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val products = snapshot.children
                    val productsList = mutableListOf<Product>()
                    if (snapshot.childrenCount > 0) {
                        for (product in products) {
                            productsList.add(
                                hashMapToProduct(
                                    product.value as HashMap<*, *>,
                                ),
                            )
                        }
                        trySend(Resource.Success(data = productsList.toList()))
                    } else {
                        trySend(Resource.Success(data = emptyList()))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(Resource.Error(error.message))
                }
            }

            reference.addValueEventListener(listener)

            awaitClose {
                reference.removeEventListener(listener)
            }
        }
    }

    override suspend fun getSingleProduct(id: String): Flow<Resource<Product>> {
        return callbackFlow {
            trySend(Resource.Loading())

            val dbReference = firebaseDateBase.reference.child(ROOT).child(PRODUCTS_NODE)
                .child(id)

            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val data = snapshot.value as HashMap<*, *>

                        trySend(Resource.Success(data = hashMapToProduct(data)))
                    } else {
                        trySend(Resource.Error(message = "Couldn't find the selected product"))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            }

            dbReference.addValueEventListener(listener)

            awaitClose {
                dbReference.removeEventListener(listener)
            }
        }
    }

    override suspend fun uploadUserReviewOnProduct(
        productId: String,
        reviews: List<ProductReviews>,
        averageRating: Float,
        userRating: Float,
    ): Flow<Resource<List<ProductReviews>>> {
        return callbackFlow {
            trySend(Resource.Loading())
            val reference = firebaseDateBase.reference.child(ROOT).child(PRODUCTS_NODE)
                .child(productId)

            val rating = userRating.div(reviews.size) + averageRating

            val updatedData = HashMap<String, Any>()
                .apply {
                    put("pReviews", reviews)
                    put("pRating", rating)
                }

            reference.updateChildren(updatedData)
                .addOnSuccessListener {
                    trySend(Resource.Success(data = reviews))
                }.addOnFailureListener {
                    trySend(Resource.Error("Failed to upload your review"))
                }

            awaitClose {}
        }
    }
}
