package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.groceryapp.R
import com.example.groceryapp.helpers.SessionManager

class StartActivity : AppCompatActivity() {

    private val delayedTime: Long = 1500
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        sessionManager = SessionManager(this)

        var handler = Handler()
        handler.postDelayed({
            checkLogin()
        }, delayedTime)
    }

    private fun checkLogin(){
        Log.d("loginstatus", "checking login")
        var isLoggedIn = sessionManager.isLoggedIn()
        var intent = if(isLoggedIn){
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }

        startActivity(intent)
        finish()
    }
}