package com.example.e_commerce.presentation.signUp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.domain.models.UserRegistration
import com.example.e_commerce.domain.useCases.UseCasesWrapper
import com.example.e_commerce.presentation.common.state.CustomTextFieldState
import com.example.e_commerce.presentation.common.state.SignInUpProcessState
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val useCasesWrapper: UseCasesWrapper,
) : ViewModel() {

    private val _userRegistrationUpState = mutableStateOf(UserRegistration())

    val userRegistrationUpState: State<UserRegistration> = _userRegistrationUpState

    private val _signingProcessState = mutableStateOf(SignInUpProcessState())
    val signingProcessState: State<SignInUpProcessState> = _signingProcessState

    fun onEvent(signUpEvent: SignUpEvents) {
        when (signUpEvent) {
            is SignUpEvents.EnteredEmail -> {
                _userRegistrationUpState.value = _userRegistrationUpState.value.copy(
                    userEmailField = CustomTextFieldState(value = signUpEvent.email),
                )
            }

            is SignUpEvents.EnteredUserName -> {
                _userRegistrationUpState.value = _userRegistrationUpState.value.copy(
                    userNameField = CustomTextFieldState(
                        value = signUpEvent.userName,
                    ),
                )
            }

            is SignUpEvents.EnteredPassword -> {
                _userRegistrationUpState.value = _userRegistrationUpState.value.copy(
                    userPasswordField = CustomTextFieldState(
                        value = signUpEvent.password,
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
                viewModelScope.launch {
                    useCasesWrapper.signUpUseCase(signUpEvent.userRegistration)
                        .collect { result ->
                            when (result) {
                                is Resource.Loading -> {
                                    _signingProcessState.value = _signingProcessState.value.copy(
                                        isLoading = true,
                                    )
                                }

                                is Resource.Success -> {
                                    _signingProcessState.value = _signingProcessState.value.copy(
                                        isSuccess = "Created user " +
                                            "${_userRegistrationUpState.value.userNameField} Successfully",
                                    )
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
        }
    }
}
