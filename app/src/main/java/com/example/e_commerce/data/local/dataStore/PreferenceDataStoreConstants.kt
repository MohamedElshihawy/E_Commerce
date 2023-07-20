package com.example.e_commerce.data.local.dataStore

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceDataStoreConstants {

    val USER_NAME_KEY = stringPreferencesKey("user_email")

    val USER_PASSWORD_KEY = stringPreferencesKey("user_password")
}
