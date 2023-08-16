package com.example.e_commerce.presentation.features.userCart.confirmOrder

import com.example.e_commerce.domain.models.UserOrder

sealed class ConfirmOrderEvents {

    class EnteredName(val name: String) : ConfirmOrderEvents()
    class EnteredPhoneNumber(val phoneNumber: String) : ConfirmOrderEvents()
    class EnteredCity(val city: String) : ConfirmOrderEvents()
    class EnteredAddress(val address: String) : ConfirmOrderEvents()

    class GetUserOrders(val orders: List<UserOrder>) : ConfirmOrderEvents()

    object SubmitRequestToAdmin:ConfirmOrderEvents()
}
