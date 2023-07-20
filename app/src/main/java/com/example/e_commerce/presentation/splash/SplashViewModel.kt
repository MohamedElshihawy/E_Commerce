package com.example.e_commerce.presentation.splash

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.data.local.dataStore.PreferenceDataStoreConstants
import com.example.e_commerce.data.local.dataStore.PreferenceDataStoreHelper
import com.example.e_commerce.domain.useCases.UseCasesWrapper
import com.example.e_commerce.presentation.common.state.SignInUpProcessState
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val useCasesWrapper: UseCasesWrapper,
    private val context: Application,
) : ViewModel() {

    private val _signInState = mutableStateOf(SignInUpProcessState())

    val signInState: State<SignInUpProcessState> = _signInState

    init {
        logInAfterDelay()
    }

    private fun logInAfterDelay() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(500)

            PreferenceDataStoreHelper(context)
                .apply {
                    getPreference(
                        key = PreferenceDataStoreConstants.USER_NAME_KEY,
                        defaultValue = "",
                    )
                        .collect { email ->
                            if (email != "") {
                                getPreference(
                                    key = PreferenceDataStoreConstants.USER_NAME_KEY,
                                    defaultValue = "",
                                )
                                    .collect { password ->
                                        if (password != "") {
                                            useCasesWrapper.signInUseCase(
                                                email = email,
                                                password = password,
                                            ).collect { result ->
                                                when (result) {
                                                    is Resource.Loading -> {
                                                        _signInState.value =
                                                            _signInState.value.copy(
                                                                isLoading = true,
                                                            )
                                                    }

                                                    is Resource.Success -> {
                                                        _signInState.value =
                                                            _signInState.value.copy(
                                                                isSuccess = "Welcome Back $email",
                                                            )
                                                    }

                                                    is Resource.Error -> {
                                                        _signInState.value =
                                                            _signInState.value.copy(
                                                                isError = "Something went wrong ,maybe you changed you password recently",
                                                            )
                                                    }
                                                }
                                            }
                                        }
                                    }
                            }
                        }
                }
        }
    }
}
