package com.example.e_commerce.presentation.signIn

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.data.local.dataStore.PreferenceDataStoreConstants
import com.example.e_commerce.data.local.dataStore.PreferenceDataStoreHelper
import com.example.e_commerce.domain.useCases.UseCasesWrapper
import com.example.e_commerce.presentation.common.state.CustomTextFieldState
import com.example.e_commerce.presentation.common.state.SignInUpProcessState
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.launch

class SignInViewModel(
    private val useCasesWrapper: UseCasesWrapper,
    private val context: Application,
) : ViewModel() {

    private val _signInScreenState = mutableStateOf(SignInScreenState())
    val signInScreenState: State<SignInScreenState> = _signInScreenState

    private val _signingProcessState = mutableStateOf(SignInUpProcessState())
    val signingProcessState: State<SignInUpProcessState> = _signingProcessState

    fun onEvent(signInEvent: SignInEvents) {
        when (signInEvent) {
            is SignInEvents.EnteredEmail -> {
                _signInScreenState.value = _signInScreenState.value.copy(
                    userEmailField = CustomTextFieldState(
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
                    useCasesWrapper.signInUseCase(signInEvent.name, signInEvent.password)
                        .collect { result ->
                            when (result) {
                                is Resource.Loading -> {
                                    _signingProcessState.value = _signingProcessState.value.copy(
                                        isLoading = true,
                                    )
                                }

                                is Resource.Success -> {
                                    if (signInEvent.name == result.data?.userEmail &&
                                        signInEvent.password == result.data.userPassword
                                    ) {
                                        _signingProcessState.value =
                                            _signingProcessState.value.copy(
                                                isSuccess = "Welcome ${result.data.userName}",
                                            )
                                        if (_signInScreenState.value.checkBox) {
                                            PreferenceDataStoreHelper(context = context)
                                                .apply {
                                                    putPreference(
                                                        key = PreferenceDataStoreConstants.USER_NAME_KEY,
                                                        value = result.data.userName,
                                                    )
                                                    putPreference(
                                                        key = PreferenceDataStoreConstants.USER_PASSWORD_KEY,
                                                        value = result.data.userPassword,
                                                    )
                                                }
                                        }
                                    }
                                }

                                is Resource.Error -> {
                                    _signingProcessState.value = _signingProcessState.value.copy(
                                        isError = result.message!!,
                                    )
                                }
                            }
                        }
                }
            }

            is SignInEvents.RememberMe -> {
                _signInScreenState.value = _signInScreenState.value.copy(
                    checkBox = signInEvent.checkBox,
                )
            }
        }
    }
}
