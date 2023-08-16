package com.example.e_commerce.presentation.features.manageUserProfile

import android.app.Application
import android.net.Uri
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.data.local.dataStore.PreferenceDataStoreConstants.USER_NAME_KEY
import com.example.e_commerce.data.local.dataStore.PreferenceDataStoreConstants.USER_PASSWORD_KEY
import com.example.e_commerce.data.local.dataStore.PreferenceDataStoreConstants.User_IMAGE_KEY
import com.example.e_commerce.data.local.dataStore.PreferenceDataStoreHelper
import com.example.e_commerce.data.local.entity.UserRegistrationEntity
import com.example.e_commerce.domain.models.UserRegistration
import com.example.e_commerce.domain.useCases.UseCasesWrapper
import com.example.e_commerce.presentation.common.state.CustomTextFieldState
import com.example.e_commerce.presentation.common.state.NetworkRequestState
import com.example.e_commerce.util.Constants.EDIT_USER
import com.example.e_commerce.util.Constants.SAVE_USER
import com.example.e_commerce.util.Resource
import com.example.e_commerce.util.containsSpecialCharacters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ManageUserProfileViewModel(
    private val useCasesWrapper: UseCasesWrapper,
    private val context: Application,
) : ViewModel() {

    private val _editOrSaveUserOption = mutableStateOf(EDIT_USER)
    val editOrSaveUserOption: State<String> = _editOrSaveUserOption

    private val _userNameState = mutableStateOf(CustomTextFieldState())
    val userNameState: State<CustomTextFieldState> = _userNameState

    private val _userEmailState = mutableStateOf(CustomTextFieldState())
    val userEmailState: State<CustomTextFieldState> = _userEmailState

    private val _userPhoneState = mutableStateOf(CustomTextFieldState())
    val userPhoneState: State<CustomTextFieldState> = _userPhoneState

    private val _userAddressState = mutableStateOf(CustomTextFieldState())
    val userAddressState: State<CustomTextFieldState> = _userAddressState

    private val _userPasswordState = mutableStateOf(CustomTextFieldState())
    val userPasswordState: State<CustomTextFieldState> = _userPasswordState

    private val _userImageState = mutableStateOf<Uri?>(null)
    val userImageState: State<Uri?> = _userImageState

    private val _getUserNetworkRequestState = mutableStateOf(NetworkRequestState())
    val getUserNetworkRequestState: State<NetworkRequestState> = _getUserNetworkRequestState

    private val _modifyUserNetworkRequestState = mutableStateOf(NetworkRequestState())
    val modifyUserNetworkRequestState: State<NetworkRequestState> = _modifyUserNetworkRequestState

    private val _enableEditMode = mutableStateOf(false)
    val enableEditMode: State<Boolean> = _enableEditMode

    init {
        viewModelScope.launch {
            getCurrentUser()
        }
    }

    fun onEvent(
        settingsEvents: ManageUserProfileScreenEvents,
    ) {
        when (settingsEvents) {
            is ManageUserProfileScreenEvents.EditUser -> {
                _enableEditMode.value = true
                _editOrSaveUserOption.value = SAVE_USER
            }

            is ManageUserProfileScreenEvents.SaveUserModification -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if (isUserDataValid()) {
                        useCasesWrapper.uploadUserProfileImageUseCase(
                            _userImageState.value ?: "".toUri(),
                        )
                            .collect { imageUrl ->
                                when (imageUrl) {
                                    is Resource.Loading -> {
                                        _modifyUserNetworkRequestState.value =
                                            _modifyUserNetworkRequestState.value.copy(
                                                isLoading = true,
                                            )
                                    }

                                    is Resource.Success -> {
                                        onEvent(
                                            ManageUserProfileScreenEvents.PickedProfileImage(
                                                imageUrl.data,
                                            ),
                                        )
                                        useCasesWrapper.updateUserData(
                                            collectUserData(),
                                        ).collect { result ->
                                            withContext(Dispatchers.Main) {
                                                when (result) {
                                                    is Resource.Loading -> {
                                                        _modifyUserNetworkRequestState.value =
                                                            _modifyUserNetworkRequestState.value.copy(
                                                                isLoading = true,
                                                            )
                                                    }

                                                    is Resource.Success -> {
                                                        _modifyUserNetworkRequestState.value =
                                                            _modifyUserNetworkRequestState.value.copy(
                                                                isLoading = false,
                                                                isSuccess = "Updated user successfully",
                                                            )
                                                        withContext(Dispatchers.IO) {
                                                            storeUserInDataStore()
                                                        }
                                                    }

                                                    is Resource.Error -> {
                                                        _modifyUserNetworkRequestState.value =
                                                            _modifyUserNetworkRequestState.value.copy(
                                                                isLoading = false,
                                                                isError = "Couldn't update user successfully",
                                                            )
                                                    }
                                                }
                                                _enableEditMode.value = false
                                                _editOrSaveUserOption.value = EDIT_USER
                                            }
                                        }
                                    }

                                    is Resource.Error -> {
                                    }
                                }
                            }
                    }
                }
            }

            is ManageUserProfileScreenEvents.EnteredEmail -> {
                _userEmailState.value = _userEmailState.value.copy(
                    value = settingsEvents.email,
                    showError = !Patterns.EMAIL_ADDRESS
                        .matcher(settingsEvents.email)
                        .matches(),
                )
            }

            is ManageUserProfileScreenEvents.EnteredUserName -> {
                _userNameState.value = _userNameState.value.copy(
                    value = settingsEvents.userName,
                    showError = containsSpecialCharacters(settingsEvents.userName) ||
                        settingsEvents.userName.length < 6,
                )
            }

            is ManageUserProfileScreenEvents.EnteredPassword -> {
                _userPasswordState.value = _userPasswordState.value.copy(
                    value = settingsEvents.password,
                    showError = settingsEvents.password.length < 8,
                )
            }

            is ManageUserProfileScreenEvents.EnteredPhoneNumber -> {
                _userPhoneState.value = _userPhoneState.value.copy(
                    value = settingsEvents.phoneNumber,
                )
            }

            is ManageUserProfileScreenEvents.EnteredAddress -> {
                _userAddressState.value = _userAddressState.value.copy(
                    value = settingsEvents.address,
                    showError = settingsEvents.address.isEmpty(),
                )
            }

            is ManageUserProfileScreenEvents.PickedProfileImage -> {
                _userImageState.value = settingsEvents.uri
            }

            else -> {
                Log.e("TAG", "onEvent: else branch ")
            }
        }
    }

    private suspend fun getCurrentUser() {
        PreferenceDataStoreHelper(context)
            .apply {
                getPreference(
                    key = USER_NAME_KEY,
                    defaultValue = "",
                ).collect { userName ->
                    useCasesWrapper.getCurrentUserUseCase(
                        userName,
                    ).collect { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _getUserNetworkRequestState.value =
                                    _getUserNetworkRequestState.value.copy(
                                        isLoading = true,
                                    )
                                Log.e("TAG", "getCurrentUser: loading")
                            }

                            is Resource.Success -> {
                                _getUserNetworkRequestState.value =
                                    _getUserNetworkRequestState.value.copy(
                                        isLoading = false,
                                        isSuccess = "Updated user successfully",
                                    )

                                withContext(Dispatchers.IO) {
                                    setUserData(result.data!!)
                                    storeUserInDataStore()
                                }
                            }

                            is Resource.Error -> {
                                _getUserNetworkRequestState.value =
                                    _getUserNetworkRequestState.value.copy(
                                        isLoading = false,
                                        isError = "Couldn't update user successfully",
                                    )
                            }
                        }
                    }
                }
            }
    }

    private suspend fun storeUserInDataStore() {
        PreferenceDataStoreHelper(context)
            .apply {
                putPreference(USER_NAME_KEY, _userNameState.value.value)

                putPreference(USER_PASSWORD_KEY, _userPasswordState.value.value)

                putPreference(User_IMAGE_KEY, _userImageState.value.toString())
            }
    }

    private fun isUserDataValid(): Boolean {
        val constraintsViolated =
            _userEmailState.value.showError
                .and(_userNameState.value.showError)
                .and(_userPhoneState.value.showError)
                .and(_userPasswordState.value.showError)
                .and(_userAddressState.value.showError)

        val emptyInput = _userEmailState.value.value.isEmpty()
            .and(_userNameState.value.value.isEmpty())
            .and(_userPhoneState.value.value.isEmpty())
            .and(_userPasswordState.value.value.isEmpty())
            .and(_userAddressState.value.value.isEmpty())

        return !constraintsViolated.and(emptyInput)
    }

    private fun collectUserData(): UserRegistrationEntity {
        return UserRegistration(
            userNameField = CustomTextFieldState(
                value = _userNameState.value.value,
            ),
            userEmailField = CustomTextFieldState(
                value = _userEmailState.value.value,
            ),
            userPasswordField = CustomTextFieldState(
                value = _userPasswordState.value.value,
            ),
            userImageField = CustomTextFieldState(
                value = _userImageState.value.toString(),
            ),
            userPhoneField = CustomTextFieldState(
                value = _userPhoneState.value.value,
            ),
            userAddressField = CustomTextFieldState(
                value = _userAddressState.value.value,
            ),
        ).toUserRegistrationEntity()
    }

    private fun setUserData(user: UserRegistrationEntity) {
        _userNameState.value = CustomTextFieldState(
            value = user.userName,
        )

        _userEmailState.value = CustomTextFieldState(
            value = user.userEmail,
        )

        _userAddressState.value = CustomTextFieldState(
            value = user.userAddress,
        )

        _userPhoneState.value = CustomTextFieldState(
            value = user.userPhone,
        )

        _userImageState.value = user.userImage.toUri()

        _userPasswordState.value = CustomTextFieldState(
            value = user.userPassword,
        )
    }
}
