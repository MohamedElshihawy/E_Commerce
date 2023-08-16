package com.example.e_commerce.presentation.features.signUp

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.domain.models.UserRegistration
import com.example.e_commerce.domain.useCases.UseCasesWrapper
import com.example.e_commerce.presentation.common.state.CustomTextFieldState
import com.example.e_commerce.presentation.common.state.NetworkRequestState
import com.example.e_commerce.util.Resource
import com.example.e_commerce.util.containsSpecialCharacters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel(
    private val useCasesWrapper: UseCasesWrapper,
) : ViewModel() {

    private val _userRegistrationUpState = mutableStateOf(UserRegistration())

    val userRegistrationUpState: State<UserRegistration> = _userRegistrationUpState

    private val _signingProcessState = mutableStateOf(NetworkRequestState())
    val signingProcessState: State<NetworkRequestState> = _signingProcessState

    fun onEvent(signUpEvent: SignUpEvents) {
        when (signUpEvent) {
            is SignUpEvents.EnteredEmail -> {
                _userRegistrationUpState.value = _userRegistrationUpState.value.copy(
                    userEmailField = CustomTextFieldState(
                        value = signUpEvent.email,
                        showError = !Patterns.EMAIL_ADDRESS
                            .matcher(signUpEvent.email)
                            .matches(),
                    ),
                )
            }

            is SignUpEvents.EnteredUserName -> {
                _userRegistrationUpState.value = _userRegistrationUpState.value.copy(
                    userNameField = CustomTextFieldState(
                        value = signUpEvent.userName,
                        showError = (
                            containsSpecialCharacters(signUpEvent.userName) ||
                                signUpEvent.userName.length < 6
                            ),
                    ),
                )
            }

            is SignUpEvents.EnteredPassword -> {
                _userRegistrationUpState.value = _userRegistrationUpState.value.copy(
                    userPasswordField = CustomTextFieldState(
                        value = signUpEvent.password,
                        showError = (signUpEvent.password.length < 8),
                    ),
                )
            }

            is SignUpEvents.EnteredPhoneNumber -> {
                _userRegistrationUpState.value = _userRegistrationUpState.value.copy(
                    userPhoneField = CustomTextFieldState(
                        value = signUpEvent.phoneNumber,
                    ),
                )
            }

            is SignUpEvents.SignUp -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if (isUserDataValid()) {
                        useCasesWrapper.signUpUseCase(signUpEvent.userRegistration)
                            .collect { result ->
                                withContext(Dispatchers.Main) {
                                    when (result) {
                                        is Resource.Loading -> {
                                            _signingProcessState.value =
                                                _signingProcessState.value.copy(
                                                    isLoading = true,
                                                )
                                        }

                                        is Resource.Success -> {
                                            _signingProcessState.value =
                                                _signingProcessState.value.copy(
                                                    isSuccess = "Created user " +
                                                        "${_userRegistrationUpState.value.userNameField.value} Successfully",
                                                    isLoading = false,
                                                )
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
            }
        }
    }

    private fun isUserDataValid(): Boolean {
        val constraintViolated = _userRegistrationUpState.value.userNameField.showError
            .or(_userRegistrationUpState.value.userEmailField.showError)
            .or(_userRegistrationUpState.value.userPhoneField.showError)
            .or(_userRegistrationUpState.value.userPasswordField.showError)
        val emptyInput = _userRegistrationUpState.value.userNameField.value.isEmpty()
            .or(_userRegistrationUpState.value.userEmailField.value.isEmpty())
            .or(_userRegistrationUpState.value.userPhoneField.value.isEmpty())
            .or(_userRegistrationUpState.value.userPasswordField.value.isEmpty())

        return !constraintViolated.and(emptyInput)
    }
}
