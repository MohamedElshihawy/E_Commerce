package com.example.e_commerce.data.remote.repository

import com.example.e_commerce.data.local.entity.UserRegistrationEntity
import com.example.e_commerce.domain.models.UserRegistration
import com.example.e_commerce.domain.repository.AuthenticationRepository
import com.example.e_commerce.util.Constants.DATABASE_TIMEOUT_MS
import com.example.e_commerce.util.Constants.USERS_NODE
import com.example.e_commerce.util.Resource
import com.example.e_commerce.util.addValueEventListenerFlow
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

class AuthenticationRepositoryImpl(
    private var dbReference: DatabaseReference,
) : AuthenticationRepository {

    override suspend fun login(
        userName: String,
        password: String,
    ): Flow<Resource<UserRegistrationEntity>> {
        return dbReference.child(USERS_NODE).child(userName)
            .addValueEventListenerFlow(UserRegistrationEntity::class.java)
    }

    override suspend fun createAccount(userRegistration: UserRegistration): Flow<Resource<UserRegistration>> {
        dbReference = dbReference.child(USERS_NODE).child(userRegistration.userNameField.value)

        val scope = CoroutineScope(Dispatchers.IO)

        return callbackFlow {
            trySend(Resource.Loading())
            val listener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        trySend(Resource.Error("user Name Already exists Try using another one"))
                    } else {
                        scope.coroutineContext.cancelChildren()

                        val timeoutJob = scope.launch {
                            delay(DATABASE_TIMEOUT_MS)
                        }
                        scope.launch {
                            try {
                                withTimeout(DATABASE_TIMEOUT_MS) {
                                    dbReference.setValue(userRegistration.toUserRegistrationEntity())
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                trySend(Resource.Success(data = userRegistration))
                                            } else {
                                                trySend(Resource.Error(message = "Failed to create a new user"))
                                            }
                                        }.await()
                                }
                            } catch (e: TimeoutCancellationException) {
                                trySend(Resource.Error(message = "Time out , try again later"))
                            } finally {
                                timeoutJob.cancel()
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(Resource.Error(message = error.message))
                    close(error.toException())
                }
            }
            dbReference.addValueEventListener(listener)
            awaitClose {
                dbReference.removeEventListener(listener)
            }
        }
    }
}
