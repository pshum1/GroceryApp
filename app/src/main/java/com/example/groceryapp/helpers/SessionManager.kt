package com.example.groceryapp.helpers

import android.content.Context
import android.util.Log
import com.example.groceryapp.models.User

class SessionManager(private var context: Context) {

    private val FILE_NAME = "user_pref"
    private val KEY_FIRST_NAME = "firstName"
    private val KEY_ID = "userId"
    private val KEY_EMAIL = "email"
    private val KEY_MOBILE = "mobile"
    private val KEY_TOKEN = "token"

    //sharePreference for user
    var sharePreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    var editor = sharePreferences.edit()


    fun saveUserInput(user: User){
        editor.putString(KEY_FIRST_NAME, user.firstName)
        editor.putString(KEY_ID, user._id)
        editor.putString(KEY_EMAIL, user.email)
        editor.putString(KEY_MOBILE, user.mobile)
        editor.commit()
    }

    fun saveToken(token: String){
        editor.putString(KEY_TOKEN, token)
        editor.commit()
    }

    fun isLoggedIn(): Boolean{
        var loggedIn = sharePreferences.getString(KEY_TOKEN, null)
        return if(loggedIn != null){
            Log.d("loginstatus", loggedIn)
            true
        } else {
            Log.d("loginstatus", "nothing here")
            false
        }
    }

    fun getUser(): User{
        var name = sharePreferences.getString(KEY_FIRST_NAME, null)
        var email = sharePreferences.getString(KEY_EMAIL, null)
        var number = sharePreferences.getString(KEY_MOBILE, null)
        return User(firstName = name, email = email, mobile = number)
    }

    fun getUserId(): String{
        return sharePreferences.getString(KEY_ID, null).toString()
    }

    fun logout(){
        //editor.remove(KEY_NAME) deletes a key
        editor.clear()
        editor.commit()
    }

}