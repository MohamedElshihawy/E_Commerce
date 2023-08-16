package com.example.e_commerce.data.remote.repository

import com.example.e_commerce.domain.models.RequestToAdmin
import com.example.e_commerce.domain.models.UserOrder
import com.example.e_commerce.domain.repository.CartOperationsRepository
import com.example.e_commerce.util.Constants.CART_ADMIN_VIEW
import com.example.e_commerce.util.Constants.CART_NODE
import com.example.e_commerce.util.Constants.CART_USER_VIEW
import com.example.e_commerce.util.Constants.ROOT
import com.example.e_commerce.util.Constants.SUBMITTED_ORDER
import com.example.e_commerce.util.Constants.WAITING_IN_CART
import com.example.e_commerce.util.HelperMethods.hashMapToUserOrder
import com.example.e_commerce.util.Resource
import com.example.e_commerce.util.TimeDateFormatting
import com.example.e_commerce.util.TimeDateFormatting.formatCurrentDateAndTime
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class CartOperationsRepositoryImpl(
    private val firebaseDatabase: FirebaseDatabase,
) : CartOperationsRepository {
    override suspend fun addOrderToCart(
        userId: String,
        order: UserOrder,
    ): Flow<Resource<Boolean>> {
        return callbackFlow {
            trySend(Resource.Loading())

            val dbReference = firebaseDatabase.reference.child(ROOT)
                .child(CART_NODE).child(CART_USER_VIEW)
                .child(userId)
                .child(WAITING_IN_CART)
                .child(order.date)

            dbReference.setValue(order)
                .addOnSuccessListener {
                    trySend(Resource.Success(true))
                }.addOnFailureListener {
                    trySend(Resource.Error(message = it.message!!))
                }

            awaitClose { }
        }
    }

    override suspend fun deleteOrderFromCart(
        userId: String,
        orderDate: String,
    ): Flow<Resource<Boolean>> {
        return callbackFlow {
            trySend(Resource.Loading())

            val dbReference = firebaseDatabase.reference.child(ROOT)
                .child(CART_NODE).child(CART_USER_VIEW)
                .child(userId)
                .child(WAITING_IN_CART)
                .child(orderDate)

            dbReference.removeValue().addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(Resource.Success(true))
                } else {
                    trySend(Resource.Error(message = "couldn't delete the product"))
                }
            }

            awaitClose { }
        }
    }

    override suspend fun deleteAllOrdersFromCart(userId: String): Flow<Resource<Boolean>> {
        return callbackFlow {
            trySend(Resource.Loading())

            val dbReference = firebaseDatabase.reference.child(ROOT)
                .child(CART_NODE).child(CART_USER_VIEW)
                .child(userId)
                .child(WAITING_IN_CART)

            dbReference.removeValue().addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(Resource.Success(true))
                } else {
                    trySend(Resource.Error(message = "couldn't delete the your Orders"))
                }
            }

            awaitClose { }
        }
    }

    override suspend fun submitUserOrderToAdmin(
        userId: String,
        request: RequestToAdmin,
    ): Flow<Resource<Boolean>> {
        return callbackFlow {
            trySend(Resource.Loading())

            var dbReference = firebaseDatabase.reference.child(ROOT)
                .child(CART_NODE)
                .child(CART_USER_VIEW)
                .child(userId)
                .child(SUBMITTED_ORDER)
                .child(formatCurrentDateAndTime(System.currentTimeMillis()))

            dbReference.setValue(request)
                .addOnCompleteListener { orderSentToAdmin ->
                    if (orderSentToAdmin.isSuccessful) {
                        dbReference = firebaseDatabase.reference.child(ROOT)
                            .child(CART_NODE)
                            .child(CART_ADMIN_VIEW)
                            .child(userId)
                            .child(TimeDateFormatting.formattedTimeAndDate)

                        dbReference.setValue(request)
                            .addOnCompleteListener { movedFromWaiting ->
                                if (movedFromWaiting.isSuccessful) {
                                    dbReference = firebaseDatabase.reference.child(ROOT)
                                        .child(CART_NODE)
                                        .child(CART_USER_VIEW)
                                        .child(userId)
                                        .child(WAITING_IN_CART)
                                        .child(TimeDateFormatting.formattedTimeAndDate)

                                    dbReference.removeValue()
                                        .addOnCompleteListener {
                                            if (it.isSuccessful) {
                                                trySend(Resource.Success(true))
                                            } else {
                                                trySend(Resource.Error("something went wrong couldn't submit order"))
                                            }
                                        }
                                } else {
                                    trySend(Resource.Error("something went wrong couldn't submit order"))
                                }
                            }
                    } else {
                        trySend(Resource.Error("something went wrong couldn't submit order"))
                    }
                }
            awaitClose { }
        }
    }

    override suspend fun getAllOrdersInCart(userId: String): Flow<Resource<List<UserOrder>>> {
        return callbackFlow {
            trySend(Resource.Loading())

            val dbReference = firebaseDatabase.reference.child(ROOT)
                .child(CART_NODE)
                .child(CART_USER_VIEW)
                .child(userId)
                .child(WAITING_IN_CART)

            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val orders = mutableListOf<UserOrder>()
                    if (snapshot.exists()) {
                        if (snapshot.childrenCount > 0) {
                            for (item in snapshot.children) {
                                orders.add(hashMapToUserOrder(item.value as HashMap<*, *>))
                            }
                            trySend(Resource.Success(data = orders))
                        }
                    } else {
                        trySend(Resource.Error("couldn't find orders"))
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
}
