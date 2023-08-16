package com.example.e_commerce.presentation.features.userCart.confirmOrder

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.domain.models.RequestToAdmin
import com.example.e_commerce.domain.models.UserOrder
import com.example.e_commerce.domain.useCases.UseCasesWrapper
import com.example.e_commerce.presentation.common.state.NetworkRequestState
import com.example.e_commerce.util.DataStoreMethods
import com.example.e_commerce.util.Resource
import com.example.e_commerce.util.TimeDateFormatting.formatCurrentDateAndTime
import com.example.e_commerce.util.containsSpecialCharacters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConfirmOrderViewModel(
    private val useCasesWrapper: UseCasesWrapper,
    private val context: Application,
) : ViewModel() {

    private val _screenState = mutableStateOf(UiState())
    val screenState: State<UiState> = _screenState

    private val _networkRequestState = mutableStateOf(NetworkRequestState())
    private val networkRequestState: State<NetworkRequestState> = _networkRequestState

    private val orders = mutableListOf<UserOrder>()
    lateinit var userName: String

    init {
        viewModelScope.launch(Dispatchers.IO) {
            userName = async {
                userName = DataStoreMethods.getUserName(context)
            }.await().toString()
        }
    }

    fun onEvent(confirmOrderEvents: ConfirmOrderEvents) {
        when (confirmOrderEvents) {
            is ConfirmOrderEvents.EnteredName -> {
                _screenState.value = _screenState.value.copy(
                    name = confirmOrderEvents.name,
                    isNameError = containsSpecialCharacters(confirmOrderEvents.name) ||
                        confirmOrderEvents.name.length < 6,
                )
            }

            is ConfirmOrderEvents.EnteredAddress -> {
                _screenState.value = _screenState.value.copy(
                    address = confirmOrderEvents.address,
                    isAddressError = confirmOrderEvents.address.isEmpty(),
                )
            }

            is ConfirmOrderEvents.EnteredCity -> {
                _screenState.value = _screenState.value.copy(
                    city = confirmOrderEvents.city,
                    isCityError = confirmOrderEvents.city.isEmpty(),
                )
            }

            is ConfirmOrderEvents.EnteredPhoneNumber -> {
                _screenState.value = _screenState.value.copy(
                    phoneNumber = confirmOrderEvents.phoneNumber,
                    isPhoneNumberError = confirmOrderEvents.phoneNumber.isEmpty(),
                )
            }

            is ConfirmOrderEvents.GetUserOrders -> {
                orders.addAll(confirmOrderEvents.orders)
            }

            is ConfirmOrderEvents.SubmitRequestToAdmin -> {
                val request = createRequestToAdmin()
                if (request != null) {
                    submitFinalOrder(request)
                }
            }
        }
    }

    private fun validateUserDate(): Boolean {
        return _screenState.value.isAddressError.or(_screenState.value.isCityError)
            .or(_screenState.value.isNameError).or(_screenState.value.isPhoneNumberError)
    }

    private fun createRequestToAdmin(): RequestToAdmin? {
        return if (!validateUserDate()) {
            RequestToAdmin(
                orders = orders,
                address = _screenState.value.address,
                userName = _screenState.value.name,
                phoneNumber = _screenState.value.phoneNumber,
                city = _screenState.value.city,
                date = formatCurrentDateAndTime(System.currentTimeMillis()),
            )
        } else {
            null
        }
    }

    private fun submitFinalOrder(request: RequestToAdmin) {
        viewModelScope.launch(Dispatchers.IO) {
            useCasesWrapper.submitUserOrders(
                userId = userName,
                request = request,
            )
                .collect { result ->
                    withContext(Dispatchers.Main) {
                        when (result) {
                            is Resource.Loading -> {
                                _networkRequestState.value = _networkRequestState.value.copy(
                                    isLoading = true,
                                )
                            }

                            is Resource.Success -> {
                                _networkRequestState.value = _networkRequestState.value.copy(
                                    isLoading = false,
                                    isSuccess = "Submitted Your order",
                                )
                                orders.clear()
                            }

                            is Resource.Error -> {
                                _networkRequestState.value = _networkRequestState.value.copy(
                                    isLoading = false,
                                    isError = "Couldn't submit your order",
                                )
                            }
                        }
                    }
                }
        }
    }
}
