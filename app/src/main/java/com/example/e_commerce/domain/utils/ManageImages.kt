package com.example.e_commerce.domain.utils

import android.net.Uri
import com.example.e_commerce.util.Resource
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

fun uploadImage(
    storageReference: StorageReference,
    imageUri: Uri,
): Flow<Resource<Uri?>> {
    return callbackFlow {
        try {
            trySend(Resource.Loading())

            val downloadUrl = storageReference.putFile(imageUri).await().storage.downloadUrl.await()

            trySend(Resource.Success(data = downloadUrl))
        } catch (e: Exception) {
            trySend(Resource.Error(message = "${e.message}"))
        }

        awaitClose {
            trySend(Resource.Error("await close"))
        }
    }
}

fun deleteImage(
    storageReference: StorageReference,
    imageUrl: String,
): Flow<Resource<Boolean>> {
    return callbackFlow {
        try {
            trySend(Resource.Loading())
            val path = imageUrl.substringAfterLast("/")
            val reference = storageReference.child(path)
            reference.delete().addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(Resource.Success(it.isSuccessful))
                } else {
                    trySend(Resource.Error("failed to delete product image"))
                }
            }
        } catch (e: Exception) {
            trySend(Resource.Error(message = "${e.message}"))
        }

        awaitClose {
            trySend(Resource.Error("await close"))
        }
    }
}
