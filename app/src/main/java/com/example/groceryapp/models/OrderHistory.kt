package com.example.groceryapp.models

import java.io.Serializable

data class OrderHistoryResult(
    val count: Int,
    val `data`: ArrayList<OrderHistoryData>,
    val error: Boolean
)

data class OrderHistoryData(
    val __v: Int,
    val _id: String,
    val date: String,
    val orderStatus: String,
    val orderSummary: OrderSummaryHistory,
    val payment: Payment,
    val products: ArrayList<Product>,
    val shippingAddress: ShippingAddress,
    val user: UserHistory,
    val userId: String
):Serializable {
    companion object{
        const val KEY_ORDER_HISTORY_DATA = "orderHistory"
    }
}

data class OrderSummaryHistory(
    val _id: String,
    val deliveryCharges: Int,
    val discount: Int,
    val orderAmount: Int,
    val ourPrice: Int,
    val totalAmount: Int
)

data class Payment(
    val _id: String,
    val paymentMode: String,
    val paymentStatus: String
)

data class Product(
    val _id: String,
    val image: String,
    val mrp: Int,
    val price: Int,
    val quantity: Int
)

data class ShippingAddress(
    val city: String,
    val houseNo: String,
    val pincode: Int,
    val streetName: String
)

data class UserHistory(
    val _id: String,
    val email: String,
    val mobile: String,
    val name: String
)