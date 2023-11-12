package com.example.e_commerce.data.remote.repository

import com.example.e_commerce.data.remote.util.HelperMethods.updateProductDetails
import com.example.e_commerce.domain.models.RequestToAdmin
import com.example.e_commerce.domain.repository.AdminCartOperationsRepository
import com.example.e_commerce.util.Constants
import com.example.e_commerce.util.Constants.ACCEPTED
import com.example.e_commerce.util.Constants.DELIVERED
import com.example.e_commerce.util.Constants.PRODUCTS_NODE
import com.example.e_commerce.util.Constants.REJECTED
import com.example.e_commerce.util.Constants.ROOT
import com.example.e_commerce.util.Constants.SUBMITTED_ORDER
import com.example.e_commerce.util.Constants.WAITING
import com.example.e_commerce.util.Resource
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class AdminCartOperationsRepositoryImpl(
    private val firebaseDatabase: FirebaseDatabase,
) : AdminCartOperationsRepository {
    override suspend fun getAllRequests(userId: String): Flow<Resource<Any>> {
        return callbackFlow {
            trySend(Resource.Loading())

            val dbReference = firebaseDatabase.reference.child(ROOT).child(Constants.CART_NODE)
                .child(Constants.CART_ADMIN_VIEW)

            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        if (snapshot.childrenCount > 0) {
                            trySend(Resource.Success(data = snapshot))
                        }
                    } else {
                        trySend(Resource.Error(message = "Didn't Find Any Request"))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(Resource.Error(error.message))
                }
            }

            dbReference.addValueEventListener(listener)

            awaitClose {
                dbReference.removeEventListener(listener)
            }
        }
    }

    override suspend fun acceptRequest(
        userId: String,
        request: RequestToAdmin,
    ): Flow<Resource<Boolean>> {
        return callbackFlow {
            trySend(Resource.Loading())

            var dbReference = firebaseDatabase.reference.child(ROOT).child(Constants.CART_NODE)
                .child(Constants.CART_ADMIN_VIEW).child(ACCEPTED).child(request.userName)
                .child(request.date)

            dbReference.setValue(request).addOnCompleteListener { upload ->
                if (upload.isSuccessful) {
                    dbReference =
                        firebaseDatabase.reference.child(ROOT).child(Constants.CART_NODE)
                            .child(Constants.CART_ADMIN_VIEW)
                            .child(WAITING).child(request.userName)
                            .child(request.date)

                    dbReference.removeValue().addOnCompleteListener { remove ->
                        if (remove.isSuccessful) {
                            dbReference = firebaseDatabase.reference.child(ROOT)
                                .child(Constants.CART_NODE).child(Constants.CART_USER_VIEW)
                                .child(request.userName).child(SUBMITTED_ORDER).child(request.date)
                            val update = HashMap<String, Any>().apply {
                                put("status", ACCEPTED)
                            }
                            dbReference.updateChildren(update).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        for (order in request.orders) {
                                            dbReference = firebaseDatabase.reference.child(ROOT)
                                                .child(PRODUCTS_NODE).child(order.productId)
                                            updateProductDetails(
                                                dbReference = dbReference,
                                                valueToUpdate = Pair(
                                                    "numOfItems",
                                                    order.numOfAvailableItems - order.numOfRequestedItems,
                                                ),
                                            ).collect { updatedValue ->
                                                if (!updatedValue) {
                                                    trySend(Resource.Error("Couldn't update product details"))
                                                }
                                            }
                                        }
                                    }
                                    trySend(Resource.Success(true))
                                } else {
                                    trySend(Resource.Error(remove.exception.toString()))
                                }
                            }
                        } else {
                            trySend(Resource.Error(remove.exception.toString()))
                        }
                    }
                } else {
                    trySend(Resource.Error(upload.exception.toString()))
                }
            }

            awaitClose { }
        }
    }

    override suspend fun rejectRequest(
        userId: String,
        request: RequestToAdmin,
    ): Flow<Resource<Boolean>> {
        return callbackFlow {
            trySend(Resource.Loading())

            var dbReference = firebaseDatabase.reference.child(ROOT).child(Constants.CART_NODE)
                .child(Constants.CART_ADMIN_VIEW).child(REJECTED)
                .child(request.userName).child(request.date)

            dbReference.setValue(request).addOnCompleteListener { upload ->
                if (upload.isSuccessful) {
                    dbReference =
                        firebaseDatabase.reference.child(ROOT).child(Constants.CART_NODE)
                            .child(Constants.CART_ADMIN_VIEW).child(WAITING)
                            .child(request.userName)
                            .child(request.date)

                    dbReference.removeValue().addOnCompleteListener { remove ->
                        if (remove.isSuccessful) {
                            dbReference = firebaseDatabase.reference.child(ROOT)
                                .child(Constants.CART_NODE).child(Constants.CART_USER_VIEW)
                                .child(request.userName).child(SUBMITTED_ORDER)
                                .child(request.date)
                            val update = HashMap<String, Any>().apply {
                                put("status", REJECTED)
                            }
                            dbReference.updateChildren(update).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    trySend(Resource.Success(true))
                                } else {
                                    trySend(Resource.Error(remove.exception.toString()))
                                }
                            }
                        } else {
                            trySend(Resource.Error(remove.exception.toString()))
                        }
                    }
                } else {
                    trySend(Resource.Error(upload.exception.toString()))
                }
            }
            awaitClose { }
        }
    }

    override suspend fun deliveredRequest(
        userId: String,
        request: RequestToAdmin,
    ): Flow<Resource<Boolean>> {
        return callbackFlow {
            trySend(Resource.Loading())

            var dbReference = firebaseDatabase.reference.child(ROOT).child(Constants.CART_NODE)
                .child(Constants.CART_ADMIN_VIEW).child(DELIVERED)
                .child(request.userName).child(request.date)

            dbReference.setValue(request).addOnCompleteListener { upload ->
                if (upload.isSuccessful) {
                    dbReference = firebaseDatabase.reference.child(ROOT).child(Constants.CART_NODE)
                        .child(Constants.CART_ADMIN_VIEW).child(ACCEPTED)
                        .child(request.userName).child(request.date)

                    dbReference.removeValue().addOnCompleteListener {
                        if (it.isSuccessful) {
                            trySend(Resource.Success(true))
                        } else {
                            trySend(Resource.Error("couldn't delete request from accepted"))
                        }
                    }
                } else {
                    trySend(Resource.Error("Couldn't change request status to delivered"))
                }
            }
            awaitClose { }
        }
    }
}
