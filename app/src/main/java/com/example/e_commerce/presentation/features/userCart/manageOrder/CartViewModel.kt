package com.example.e_commerce.presentation.features.userCart.manageOrder

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.data.local.dataStore.PreferenceDataStoreConstants
import com.example.e_commerce.data.local.dataStore.PreferenceDataStoreHelper
import com.example.e_commerce.domain.models.UserOrder
import com.example.e_commerce.domain.useCases.UseCasesWrapper
import com.example.e_commerce.presentation.common.state.NetworkRequestState
import com.example.e_commerce.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartViewModel(
    private val useCasesWrapper: UseCasesWrapper,
    private val context: Application,
) : ViewModel() {

    private val _userOrdersState = mutableStateOf(listOf<UserOrder>())
    val userOrdersState: State<List<UserOrder>> = _userOrdersState

    private val _itemsCountersState = mutableStateListOf<SingleOrderState>()
    val itemsCounters = _itemsCountersState

    private val _totalPriceState = mutableStateOf(0f)
    val totalPriceState: State<Float> = _totalPriceState

    private val _numOfSelectedItems = mutableStateOf(0)
    val numOfSelectedItems: State<Int> = _numOfSelectedItems

    private val _networkRequestState = mutableStateOf(NetworkRequestState())
    val networkRequestState: State<NetworkRequestState> = _networkRequestState

    private var userName = ""

    fun onEvent(cartEvents: CartEvents) {
        when (cartEvents) {
            is CartEvents.DecreaseCounter -> {
                _itemsCountersState.forEach {
                    if (it.id == cartEvents.counterId) {
                        it.decreaseValue()
                        updateOrderState(count = it.count, cartEvents.counterId)
                    }
                }
            }

            is CartEvents.IncreaseCounter -> {
                val availableItems =
                    _userOrdersState.value.first { it.date == cartEvents.counterId }.numOfAvailableItems
                _itemsCountersState.forEach {
                    if (it.id == cartEvents.counterId) {
                        if (availableItems > it.count) {
                            it.increaseCount()
                            updateOrderState(count = it.count, cartEvents.counterId)
                        }
                    }
                }
            }

            is CartEvents.DeleteSingleItem -> {
                deleteOrderFromCart(
                    orderId = cartEvents.itemId,
                )
            }

            is CartEvents.DeleteAllItems -> {
                deleteAllOrders()
            }

            is CartEvents.SelectSingleItem -> {
                var item: SingleOrderState? = null
                var itemIndex = 0
                _itemsCountersState.forEachIndexed { index, order ->
                    if (order.id == cartEvents.itemId) {
                        itemIndex = index
                        item = order
                    }
                }

                val newItem = SingleOrderState(
                    count = item!!.count,
                    id = item!!.id,
                    isSelected = !item!!.isSelected,
                )
                _itemsCountersState[itemIndex] = newItem
            }

            is CartEvents.SelectAllItems -> {
                _itemsCountersState.forEachIndexed { index, order ->
                    _itemsCountersState[index] = SingleOrderState(
                        id = order.id,
                        count = order.count,
                        isSelected = !order.isSelected,
                    )
                }
            }

            is CartEvents.CalculateTotalPrice -> {
                var sum = 0f
                _userOrdersState.value.forEach { userOrder ->
                    sum += userOrder.totalPrice
                }
                _totalPriceState.value = sum
            }

            is CartEvents.CreateNewCounter -> {
                val counterExist = _itemsCountersState.any { it.id == cartEvents.counterId }
                if (!counterExist) {
                    _itemsCountersState.add(
                        SingleOrderState(
                            id = cartEvents.counterId,
                        ),
                    )
                }
            }
        }
    }

    fun getUserOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            PreferenceDataStoreHelper(context)
                .getPreference(PreferenceDataStoreConstants.USER_NAME_KEY, "")
                .collect { name ->
                    userName = name
                    useCasesWrapper.getAllOrdersInCartUseCase(userName)
                        .collect { result ->
                            withContext(Dispatchers.Main) {
                                when (result) {
                                    is Resource.Loading -> {
                                        _networkRequestState.value =
                                            _networkRequestState.value.copy(
                                                isLoading = true,
                                            )
                                        Log.e("TAG", "getUserOrders: loading")
                                    }

                                    is Resource.Success -> {
                                        _networkRequestState.value =
                                            _networkRequestState.value.copy(
                                                isLoading = false,
                                                isSuccess = "success",
                                            )
                                        Log.e("TAG", "getUserOrders: ${result.data!!.size} ")
                                        _userOrdersState.value = result.data
                                    }

                                    is Resource.Error -> {
                                        _networkRequestState.value =
                                            _networkRequestState.value.copy(
                                                isLoading = false,
                                                isError = "error",
                                            )
                                        Log.e("TAG", "getUserOrders: error ${result.message} ")
                                    }
                                }
                            }
                        }
                }
        }
    }

    private fun deleteOrderFromCart(orderId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCasesWrapper.deleteOrderFromCart(
                userId = userName,
                orderDate = orderId,
            ).collect { result ->
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
                                isSuccess = "Deleted order Successfully",
                            )
                            _userOrdersState.value = _userOrdersState.value.toMutableList()
                                .filter { it.date != orderId }
                        }

                        is Resource.Error -> {
                            _networkRequestState.value = _networkRequestState.value.copy(
                                isLoading = false,
                                isError = "Couldn't delete your order",
                            )
                        }
                    }
                }
            }
        }
    }

    private fun deleteAllOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            useCasesWrapper.deleteAllOrdersFromCartUserCase(userName)
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
                                    isSuccess = "Deleted All Orders",
                                )
                                _userOrdersState.value = emptyList()
                            }

                            is Resource.Error -> {
                                _networkRequestState.value = _networkRequestState.value.copy(
                                    isLoading = false,
                                    isError = "Couldn't Delete All Orders",
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun updateOrderState(count: Int, id: String) {
        var itemIndex = 0
        var item: UserOrder? = null


        _userOrdersState.value.forEachIndexed { index, order ->
            if (order.date == id) {
                item = order
                itemIndex = index
            }
        }

        val newItem = UserOrder(
            productId = item!!.productId,
            date = item!!.date,
            name = item!!.name,
            numOfAvailableItems = item!!.numOfAvailableItems,
            numOfRequestedItems = count,
            unitPrice = item!!.unitPrice,
            image = item!!.image,
            totalPrice = count * item!!.unitPrice,
        )
        val updatedList = _userOrdersState.value.toMutableList()
        updatedList[itemIndex] = newItem
        _userOrdersState.value = updatedList
    }
}
