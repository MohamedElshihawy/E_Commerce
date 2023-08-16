package com.example.e_commerce.presentation.features.manageUserProfile

import android.net.Uri

sealed class ManageUserProfileScreenEvents {

    data class EnteredUserName(val userName: String) : ManageUserProfileScreenEvents()

    data class EnteredEmail(val email: String) : ManageUserProfileScreenEvents()

    data class EnteredPhoneNumber(val phoneNumber: String) : ManageUserProfileScreenEvents()

    data class EnteredPassword(val password: String) : ManageUserProfileScreenEvents()
    data class EnteredAddress(val address: String) : ManageUserProfileScreenEvents()
    data class PickedProfileImage(val uri: Uri?) : ManageUserProfileScreenEvents()

    object EditUser : ManageUserProfileScreenEvents()
    object SaveUserModification : ManageUserProfileScreenEvents()
}
