package com.example.e_commerce.presentation.features.signIn

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.data.local.dataStore.PreferenceDataStoreConstants
import com.example.e_commerce.data.local.dataStore.PreferenceDataStoreHelper
import com.example.e_commerce.domain.useCases.UseCasesWrapper
import com.example.e_commerce.presentation.common.state.CustomTextFieldState
import com.example.e_commerce.presentation.common.state.NetworkRequestState
import com.example.e_commerce.util.Constants.ADMINS_NODE
import com.example.e_commerce.util.Constants.USERS_NODE
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.launch

class SignInViewModel(
    private val useCasesWrapper: UseCasesWrapper,
    private val context: Application,
) : ViewModel() {

    private val _signInScreenState = mutableStateOf(SignInScreenState())
    val signInScreenState: State<SignInScreenState> = _signInScreenState

    private val _signingProcessState = mutableStateOf(NetworkRequestState())
    val signingProcessState: State<NetworkRequestState> = _signingProcessState

    private val _currentUserOrAdmin = mutableStateOf(USERS_NODE)
    val currentUserOrAdmin: State<String> = _currentUserOrAdmin

    fun onEvent(signInEvent: SignInEvents) {
        when (signInEvent) {
            is SignInEvents.EnteredEmail -> {
                _signInScreenState.value = _signInScreenState.value.copy(
                    userNameField = CustomTextFieldState(
                        value = signInEvent.email,
                    ),
                )
            }

            is SignInEvents.EnteredPassword -> {
                _signInScreenState.value = _signInScreenState.value.copy(
                    userPasswordField = CustomTextFieldState(
                        value = signInEvent.password,
                    ),
                )
            }

            is SignInEvents.SignIn -> {
                viewModelScope.launch {
                    if (_currentUserOrAdmin.value == USERS_NODE) {
                        signIn(
                            dbParentNode = _currentUserOrAdmin.value,
                            signInEvent.name,
                            signInEvent.password,
                        )
                    } else if (_currentUserOrAdmin.value == ADMINS_NODE) {
                        signIn(
                            dbParentNode = _currentUserOrAdmin.value,
                            signInEvent.name,
                            signInEvent.password,
                        )
                    }
                }
            }

            is SignInEvents.RememberMe -> {
                _signInScreenState.value = _signInScreenState.value.copy(
                    checkBox = signInEvent.checkBox,
                )
            }

            is SignInEvents.ChangeToAdminLogin -> {
                _currentUserOrAdmin.value = ADMINS_NODE
            }

            is SignInEvents.ChangeToUserLogin -> {
                _currentUserOrAdmin.value = USERS_NODE
            }
        }
    }

    private suspend fun signIn(dbParentNode: String, name: String, password: String) {
        val constraintViolated = _signInScreenState.value.userNameField.showError
            .or(_signInScreenState.value.userPasswordField.showError)

        val emptyInput = _signInScreenState.value.userNameField.value.isEmpty()
            .or(_signInScreenState.value.userPasswordField.value.isEmpty())

        if (!constraintViolated && !emptyInput) {
            useCasesWrapper.signInUseCase(dbParentNode, name, password)
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _signingProcessState.value =
                                _signingProcessState.value.copy(
                                    isLoading = true,
                                )
                        }

                        is Resource.Success -> {
                            if (name == result.data?.userNameField?.value &&
                                password == result.data.userPasswordField.value
                            ) {
                                _signingProcessState.value =
                                    if (_currentUserOrAdmin.value == USERS_NODE) {
                                        _signingProcessState.value.copy(
                                            isSuccess = "Welcome User ${result.data.userNameField.value}",
                                            isLoading = false,
                                        )
                                    } else {
                                        _signingProcessState.value.copy(
                                            isSuccess = "Welcome Admin ${result.data.userNameField.value}",
                                            isLoading = false,
                                        )
                                    }

                                if (_signInScreenState.value.checkBox) {
                                    PreferenceDataStoreHelper(context = context)
                                        .apply {
                                            putPreference(
                                                key = PreferenceDataStoreConstants.USER_NAME_KEY,
                                                value = result.data.userNameField.value,
                                            )
                                            putPreference(
                                                key = PreferenceDataStoreConstants.USER_PASSWORD_KEY,
                                                value = result.data.userPasswordField.value,
                                            )
                                            putPreference(
                                                key = PreferenceDataStoreConstants.USER_AUTHORITY_KEY,
                                                value = _currentUserOrAdmin.value,
                                            )
                                        }
                                }
                            }
                        }

                        is Resource.Error -> {
                            _signingProcessState.value =
                                _signingProcessState.value.copy(
                                    isError = result.message!!,
                                    isLoading = false,
                                )
                        }
                    }
                }
        }
    }
}
