package com.example.groceryapp.models

data class OrderSummary(
    val mrp: Double,
    val discount: Double,
    val total: Double,
    val delivery: Double,
){
    companion object{
        //KEYS FOR POSTING DATA
        const val KEY_ORDER_STATUS = "orderStatus"
        const val KEY_ORDER_SUMMARY = "orderSummary"
        const val KEY_USER = "user"
        const val KEY_SHIPPING_ADDRESS = "shippingAddress"
        const val KEY_PAYMENT = "payment"
        const val KEY_PRODUCTS = "products"
        const val KEY_DATE = "date"

        //KEYS FOR POSTING ORDER SUMMARY
        const val KEY_OUR_PRICE = "ourPrice"
        const val KEY_DISCOUNT = "discount"
        const val KEY_TOTAL = "totalAmount"
        const val KEY_DELIVERY = "deliveryCharges"
        const val KEY_ORDER_AMOUNT = "orderAmount"

        //KEYS FOR POSTING PAYMENT
        const val KEY_PAYMENT_MODE = "paymentMode"
        const val KEY_PAYMENT_STATUS = "paymentStatus"
    }
}