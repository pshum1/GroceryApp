package com.example.groceryapp.models

data class UserResponse(
    val token: String,
    val user: User
)

data class User(
    val _id: String? = null,
    val email: String? = null,
    val firstName: String? = null,
    val mobile: String? = null,
    val password: String? = null,
    val token: String? = null,
) {
    companion object{
        const val KEY_FIRST_NAME = "firstName"
        const val KEY_EMAIL = "email"
        const val KEY_PASSWORD = "password"
        const val KEY_MOBILE = "mobile"
        const val KEY_USER_ID = "userId"
        const val KEY_NAME = "name"
    }
}
