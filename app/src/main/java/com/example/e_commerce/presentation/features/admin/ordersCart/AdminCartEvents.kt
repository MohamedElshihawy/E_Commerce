package com.example.e_commerce.presentation.features.admin.ordersCart

sealed class AdminCartEvents {

    class RejectOrder(val id: String) : AdminCartEvents()
    class AcceptOrder(val id: String) : AdminCartEvents()
    class DeliverOrder(val id: String) : AdminCartEvents()
    class CreateOrderState(val id: String) : AdminCartEvents()
    class ExpandRequestContent(val id: String) : AdminCartEvents()
    object ToggleFilterList : AdminCartEvents()
    class FilterRequests(val type: String) : AdminCartEvents()
}
