package com.example.e_commerce.util

import android.app.Application
import com.example.e_commerce.data.local.dataStore.PreferenceDataStoreConstants
import com.example.e_commerce.data.local.dataStore.PreferenceDataStoreHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

object DataStoreMethods {
    suspend fun getUserName(context: Application): String {
        val userNameFlow: Flow<String> = PreferenceDataStoreHelper(context)
            .getPreference(PreferenceDataStoreConstants.USER_NAME_KEY, "")

        var userName = ""
        withContext(Dispatchers.IO) {
            userNameFlow.collect { collectedUserName ->
                userName = collectedUserName
            }
        }
        return userName
    }
}
