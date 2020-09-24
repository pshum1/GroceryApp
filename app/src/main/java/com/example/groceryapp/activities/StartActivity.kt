package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.groceryapp.R

class StartActivity : AppCompatActivity() {

    private val delayedTime: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        var handler = Handler()
        handler.postDelayed({
            checkLogin()
        }, delayedTime)
    }

    private fun checkLogin(){
        var isLoggedIn = true
        var intent = if(isLoggedIn){
            Intent(this, MainActivity::class.java)
        } else {
            //
            Intent(this, LoginActivity::class.java)
        }

        startActivity(intent)
        finish()
    }
}