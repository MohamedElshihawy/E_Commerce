package com.example.e_commerce.util

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun <T> DatabaseReference.addValueEventListenerFlow(dataType: Class<T>): Flow<Resource<T>> =
    callbackFlow {
        trySend(Resource.Loading())
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val value = dataSnapshot.getValue(dataType)
                    trySend(Resource.Success(data = value!!))
                    Log.e("TAG", "onDataChange: success", )
                } else {
                    trySend(Resource.Error("This user isn't registered"))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Resource.Error(message = error.message))
                trySend(Resource.Error("This user isn't registered"))
                close(error.toException())
            }
        }
        addValueEventListener(listener)
        awaitClose { removeEventListener(listener) }
    }

fun <T> DocumentReference.addSnapshotListenerFlow(dataType: Class<T>): Flow<Resource<T>> =
    callbackFlow {
        val listener = object : EventListener<DocumentSnapshot> {
            override fun onEvent(
                snapshot: DocumentSnapshot?,
                exception: FirebaseFirestoreException?,
            ) {
                if (exception != null) {
                    trySend(Resource.Error("Something went wrong please try again"))
                    cancel()
                    return
                }
                if (snapshot != null && snapshot.exists()) {
                    val data = snapshot.toObject(dataType)
                    trySend(Resource.Success(data = data!!))
                } else {
                    trySend(Resource.Error(message = "The data isn't found"))
                }
            }
        }

        val registration = addSnapshotListener(listener)
        awaitClose { registration.remove() }
    }

fun containsSpecialCharacters(input: String): Boolean = Regex("[.#$\\[\\]]").containsMatchIn(input)



