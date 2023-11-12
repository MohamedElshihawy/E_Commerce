package com.example.e_commerce.presentation.features.admin.ordersCart

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.data.local.dataStore.PreferenceDataStoreConstants.USER_NAME_KEY
import com.example.e_commerce.data.local.dataStore.PreferenceDataStoreHelper
import com.example.e_commerce.domain.models.RequestToAdmin
import com.example.e_commerce.domain.useCases.UseCasesWrapper
import com.example.e_commerce.domain.utils.parsers.RequestToAdminParser.hashMapToRequestToAdmin
import com.example.e_commerce.presentation.common.state.NetworkRequestState
import com.example.e_commerce.util.Constants
import com.example.e_commerce.util.Resource
import com.google.firebase.database.DataSnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdminCartViewModel(
    private val useCasesWrapper: UseCasesWrapper,
    private val context: Application,
) : ViewModel() {

    private val _userRequestsState =
        mutableStateOf<List<RequestToAdmin>>(listOf())

    val userRequestsState: State<List<RequestToAdmin>> = _userRequestsState

    private val _acceptedRequestsState = mutableStateOf<List<RequestToAdmin>>(listOf())

    private val _rejectedRequestsState = mutableStateOf<List<RequestToAdmin>>(listOf())

    private val _newRequestsState = mutableStateOf<List<RequestToAdmin>>(listOf())

    private val _deliveredRequestsState = mutableStateOf<List<RequestToAdmin>>(listOf())

    private val _requestsSectionVisible = mutableStateOf(false)
    val requestsSectionVisible: State<Boolean> = _requestsSectionVisible

    private val _selectedRequestsType = mutableStateOf("Waiting")
    val selectedRequestsType: State<String> = _selectedRequestsType

    private val _networkRequestState = mutableStateOf(NetworkRequestState())
    val networkRequestState: State<NetworkRequestState> = _networkRequestState

    private val _expandedItems = mutableStateListOf<RequestItemState>()
    val expandedItems: List<RequestItemState> = _expandedItems

    private var userName = ""

    fun onEvent(adminCartEvents: AdminCartEvents) {
        when (adminCartEvents) {
            is AdminCartEvents.AcceptOrder -> {
                acceptRequest(adminCartEvents.id)
            }

            is AdminCartEvents.RejectOrder -> {
                rejectRequest(adminCartEvents.id)
            }

            is AdminCartEvents.CreateOrderState -> {
                _expandedItems.add(
                    RequestItemState(
                        id = adminCartEvents.id,
                    ),
                )
            }

            is AdminCartEvents.ExpandRequestContent -> {
                expandRequestContent(adminCartEvents.id)
            }

            is AdminCartEvents.ToggleFilterList -> {
                _requestsSectionVisible.value = _requestsSectionVisible.value.not()
            }

            is AdminCartEvents.FilterRequests -> {
                _selectedRequestsType.value = adminCartEvents.type
                filterRequests()
            }

            is AdminCartEvents.DeliverOrder -> {
                requestDeliveredToUser(adminCartEvents.id)
            }
        }
    }

    private fun filterRequests() {
        when (_selectedRequestsType.value) {
            "Waiting" -> {
                _userRequestsState.value = _newRequestsState.value
            }

            "Accepted" -> {
                _userRequestsState.value = _acceptedRequestsState.value
            }

            "Rejected" -> {
                _userRequestsState.value = _rejectedRequestsState.value
            }

            "Delivered" -> {
                _userRequestsState.value = _deliveredRequestsState.value
            }
        }
    }

    fun getAllRequests() {
        viewModelScope.launch(Dispatchers.IO) {
            userName =
                PreferenceDataStoreHelper(context = context).getPreference(USER_NAME_KEY, "")
                    .first()

            useCasesWrapper.getAllRequestAdminCart(userId = userName)
                .collect { result ->
                    withContext(Dispatchers.Main) {
                        when (result) {
                            is Resource.Loading -> {
                                _networkRequestState.value = _networkRequestState.value.copy(
                                    isLoading = true,
                                )
                                Log.e("TAG", "getAllRequests: loading requests")
                            }

                            is Resource.Success -> {
                                _networkRequestState.value = _networkRequestState.value.copy(
                                    isLoading = false,
                                    isSuccess = "Got All Requests",
                                )
                                updateRequestsLists(result.data as DataSnapshot)
                                filterRequests()
                            }

                            is Resource.Error -> {
                                _networkRequestState.value = _networkRequestState.value.copy(
                                    isLoading = false,
                                    isError = "Couldn't get any Requests",
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun requestDeliveredToUser(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCasesWrapper.acceptRequestAdminCartUseCase(
                userName,
                _acceptedRequestsState.value.first { it.date == date },
            )
                .collect { result ->
                    handleFlowResult(result)
                }
        }
    }

    private fun acceptRequest(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCasesWrapper.acceptRequestAdminCartUseCase(
                userName,
                _newRequestsState.value.first { it.date == date },
            )
                .collect { result ->
                    handleFlowResult(result)
                }
        }
    }

    private fun rejectRequest(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCasesWrapper.rejectRequestAdminCartUseCase(
                userName,
                _newRequestsState.value.first { it.date == date },
            )
                .collect { result ->
                    handleFlowResult(result)
                }
        }
    }

    private suspend fun handleFlowResult(result: Resource<Boolean>) {
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
                        isSuccess = "success",
                    )
                }

                is Resource.Error -> {
                    _networkRequestState.value = _networkRequestState.value.copy(
                        isLoading = false,
                        isError = "failed",
                    )
                }
            }
        }
    }

    private fun expandRequestContent(id: String) {
        val item = _expandedItems.first { it.id == id }
        val newItem = RequestItemState(id = item.id, isExpanded = item.isExpanded.not())
        val itemIndex = _expandedItems.indexOf(item)
        val newList = _expandedItems.toMutableList()
        newList[itemIndex] = newItem
        _expandedItems.clear()
        _expandedItems.addAll(newList)
    }

    private fun parseAndFilterRequestsToAdmin(
        dataSnapshot: DataSnapshot,
        key: String,
    ): List<RequestToAdmin> {
        val selectedHashMap = dataSnapshot.child(key).value?.let { it as HashMap<*, *> }
        return mutableListOf<RequestToAdmin>().apply {
            if (selectedHashMap != null && selectedHashMap.isNotEmpty()) {
                selectedHashMap.values.forEach { user ->
                    for (order in (user as HashMap<*, *>).values) {
                        add(hashMapToRequestToAdmin(order as HashMap<*, *>))
                    }
                }
            }
        }
    }

    private fun updateRequestsLists(dataSnapshot: DataSnapshot) {
        _acceptedRequestsState.value = parseAndFilterRequestsToAdmin(
            dataSnapshot = dataSnapshot,
            key = Constants.ACCEPTED,
        )

        _rejectedRequestsState.value = parseAndFilterRequestsToAdmin(
            dataSnapshot = dataSnapshot,
            key = Constants.REJECTED,
        )

        _newRequestsState.value = parseAndFilterRequestsToAdmin(
            dataSnapshot = dataSnapshot,
            key = Constants.WAITING,
        )

        _deliveredRequestsState.value = parseAndFilterRequestsToAdmin(
            dataSnapshot = dataSnapshot,
            key = Constants.DELIVERED,
        )
    }
}
