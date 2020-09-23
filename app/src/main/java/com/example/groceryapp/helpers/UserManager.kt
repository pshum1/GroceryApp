package com.example.groceryapp.helpers

import android.content.Context
import com.example.groceryapp.models.User

class UserManager(private var context: Context) {

    private val FILE_NAME = "user_pref"
//    private val KEY_FIRST_NAME = "firstName"
    private val KEY_PASSWORD = "password"
    private val KEY_ID = "userId"
    private val KEY_TOKEN = "token"

    var sharePreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    var editor = sharePreferences.edit()

    private fun saveDataInput(user: User){

    }

}