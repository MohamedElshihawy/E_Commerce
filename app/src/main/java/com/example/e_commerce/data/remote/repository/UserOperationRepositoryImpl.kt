package com.example.e_commerce.data.remote.repository

import android.net.Uri
import android.util.Log
import com.example.e_commerce.data.local.entity.UserRegistrationEntity
import com.example.e_commerce.domain.repository.UserOperationRepository
import com.example.e_commerce.domain.utils.parsers.UserRegistrationParser.userRegistrationEntityToHashMap
import com.example.e_commerce.domain.utils.uploadImage
import com.example.e_commerce.util.Constants
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

class UserOperationRepositoryImpl(
    private val db: FirebaseDatabase,
    private val storage: FirebaseStorage,
) : UserOperationRepository {
    override suspend fun getCurrentUser(userName: String): Flow<Resource<UserRegistrationEntity>> {
        val dbReference = db.reference
            .child(Constants.ROOT).child(Constants.USERS_NODE)
            .child(userName)

        return callbackFlow {
            trySend(Resource.Loading())

            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        trySend(Resource.Success(data = snapshot.getValue(UserRegistrationEntity::class.java)!!))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(Resource.Error(message = error.message))
                }
            }

            dbReference.addValueEventListener(listener)

            awaitClose { dbReference.removeEventListener(listener) }
        }
    }

    override suspend fun updateUserData(userRegistration: UserRegistrationEntity): Flow<Resource<UserRegistrationEntity>> {
        val dbReference = db.reference
            .child(Constants.ROOT).child(Constants.USERS_NODE)
            .child(userRegistration.userName)

        return callbackFlow {
            trySend(Resource.Loading())

            dbReference.updateChildren(
                userRegistrationEntityToHashMap(
                    userRegistration,
                ),
            )
                .addOnSuccessListener {
                    trySend(Resource.Success(data = userRegistration))
                }
                .addOnFailureListener {
                    trySend(Resource.Error(message = it.message.toString()))
                }

            awaitClose {
                Log.e("TAG", "updateUserData: close flow")
            }
        }
    }

    override suspend fun uploadUserProfileImage(uri: Uri): Flow<Resource<Uri?>> {
        val randomProductKey =
            TimeDateFormatting.formatCurrentDateAndTime(System.currentTimeMillis())

        val reference = storage.reference.child(Constants.USERS_IMAGES)
            .child(randomProductKey)

        Log.e("TAG", "uploadUserProfileImage: ${reference.path}")

        return uploadImage(reference, uri)
    }
}
