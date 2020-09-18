package com.example.groceryapp.models

data class CategoriesResult(
    val count: Int,
    val `data`: ArrayList<CategoriesData>,
    val error: Boolean
)

data class CategoriesData(
    val __v: Int,
    val _id: String,
    val catDescription: String,
    val catId: Int,
    val catImage: String,
    val catName: String,
    val position: Int,
    val slug: String,
    val status: Boolean

)