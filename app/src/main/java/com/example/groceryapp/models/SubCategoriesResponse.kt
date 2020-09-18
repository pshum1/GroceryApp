package com.example.groceryapp.models

data class SubCategoryResult(
    val count: Int,
    val `data`: ArrayList<SubCatData>,
    val error: Boolean
)

data class SubCatData(
    val __v: Int,
    val _id: String,
    val catId: Int,
    val position: Int,
    val status: Boolean,
    val subDescription: String,
    val subId: Int,
    val subImage: String,
    val subName: String
)