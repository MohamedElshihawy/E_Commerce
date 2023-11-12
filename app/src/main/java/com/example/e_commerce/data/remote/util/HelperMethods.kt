package com.example.e_commerce.data.remote.util

import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

object HelperMethods {

    fun updateProductDetails(
        dbReference: DatabaseReference,
        valueToUpdate: Pair<String, Int>,
    ): Flow<Boolean> {
        return callbackFlow {
            val update = HashMap<String, Any>()

            update[valueToUpdate.first] = valueToUpdate.second

            dbReference.updateChildren(update)
                .addOnSuccessListener {
                    trySend(true)
                }.addOnFailureListener {
                    trySend(false)
                }
        }
    }
}
