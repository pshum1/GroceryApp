package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.helpers.SessionManager
import com.example.groceryapp.models.UserResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(this)
        init()
    }

    private fun init() {


        button_register.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        button_login.setOnClickListener{
            progress_bar_login.visibility = View.VISIBLE

            var paramsLogin = HashMap<String, String>()

            val email = et_login_email.text.toString()
            paramsLogin["email"] = email
            paramsLogin["password"] = et_login_password.text.toString()

            var jsonObjectLogin = JSONObject(paramsLogin as Map<*, *>)

            var request = JsonObjectRequest(Request.Method.POST, Endpoints.getLogin(), jsonObjectLogin,{
                val gson = Gson()
                var loginResult = gson.fromJson(it.toString(), UserResponse::class.java)

                val token = it.getString("token")
                Log.d("login", "token: $token")

                sessionManager.saveUserInput(loginResult.user)
                sessionManager.saveToken(token)

                progress_bar_login.visibility = View.GONE

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, {
                Toast.makeText(applicationContext, "Incorrect email/password!", Toast.LENGTH_SHORT).show()
            })
            Volley.newRequestQueue(this).add(request)
        }

    }
}