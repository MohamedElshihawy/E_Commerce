package com.example.e_commerce.data.local.dataStore

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceDataStoreConstants {

    val USER_NAME_KEY = stringPreferencesKey("user_name")

    val USER_PASSWORD_KEY = stringPreferencesKey("user_password")

    val User_IMAGE_KEY = stringPreferencesKey("user_image")

    val USER_AUTHORITY_KEY = stringPreferencesKey("user_authority")
}
