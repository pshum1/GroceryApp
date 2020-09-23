package com.example.groceryapp.models

import java.io.Serializable

data class ProductsResult(
    val count: Int,
    val `data`: ArrayList<ProductData>,
    val error: Boolean
)

data class ProductData(
    val __v: Int,
    val _id: String,
    val catId: Int,
    val created: String,
    val description: String,
    val image: String,
    val mrp: Double,
    val position: Int,
    val price: Double,
    val productName: String,
    val quantity: Int,
    val status: Boolean,
    val subId: Int,
    val unit: String
): Serializable{
    companion object {
        const val KEY_SUB_ID = "subId"
        const val KEY_PRODUCT = "product"
    }
}

data class Products(
    val _id: String,
    val productName: String,
    val img: String,
    val mrp: Double,
    val price: Double,
    val quantity: Int,
)
