package com.example.e_commerce.presentation.features.splash

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.data.local.dataStore.PreferenceDataStoreConstants
import com.example.e_commerce.data.local.dataStore.PreferenceDataStoreHelper
import com.example.e_commerce.domain.useCases.UseCasesWrapper
import com.example.e_commerce.presentation.common.state.NetworkRequestState
import com.example.e_commerce.util.Constants.ADMINS_NODE
import com.example.e_commerce.util.Constants.USERS_NODE
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val useCasesWrapper: UseCasesWrapper,
    private val context: Application,
) : ViewModel() {

    private val _signInState = mutableStateOf(NetworkRequestState())

    val signInState: State<NetworkRequestState> = _signInState

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
                        .collect { name ->
                            if (name != "") {
                                getPreference(
                                    key = PreferenceDataStoreConstants.USER_PASSWORD_KEY,
                                    defaultValue = "",
                                )
                                    .collect { password ->
                                        Log.e("TAG", "logInAfterDelay: $password")
                                        if (password != "") {
                                            getPreference(
                                                key = PreferenceDataStoreConstants.USER_AUTHORITY_KEY,
                                                defaultValue = "",
                                            ).collect { userAuthority ->
                                                Log.e("TAG", "logInAfterDelay: $userAuthority")
                                                useCasesWrapper.signInUseCase(
                                                    dbParentNode =
                                                    if (userAuthority == USERS_NODE) USERS_NODE else ADMINS_NODE,
                                                    email = name,
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
                                                                    isSuccess = "Welcome Back $name",
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
}
