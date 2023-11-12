package com.example.e_commerce.domain.utils.parsers

import com.example.e_commerce.data.local.entity.UserRegistrationEntity
import com.example.e_commerce.util.Constants

object UserRegistrationParser {
    fun hashMapToUserRegistrationEntity(hashMap: HashMap<*, *>): UserRegistrationEntity {
        return UserRegistrationEntity(
            userName = hashMap[Constants.USER_NAME] as String,
            userAddress = hashMap[Constants.USER_ADDRESS] as String,
            userPassword = hashMap[Constants.USER_PASSWORD] as String,
            userImage = hashMap[Constants.USER_IMAGE] as String,
            userEmail = hashMap[Constants.USER_EMAIL] as String,
            userPhone = hashMap[Constants.USER_PHONE] as String,
        )
    }

    fun userRegistrationEntityToHashMap(user: UserRegistrationEntity): HashMap<String, Any> {
        return HashMap<String, Any>().apply {
            put(Constants.USER_NAME, user.userName)
            put(Constants.USER_IMAGE, user.userImage)
            put(Constants.USER_EMAIL, user.userEmail)
            put(Constants.USER_PASSWORD, user.userPassword)
            put(Constants.USER_ADDRESS, user.userAddress)
            put(Constants.USER_PHONE, user.userPhone)
        }
    }
}
