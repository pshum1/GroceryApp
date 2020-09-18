package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
    }

    private fun init() {
        var firstName = "pp123b27"
        var email = "b27pshum1@b27.com"
        var password = "1234567"
        var mobile = "1234"



        button_register.setOnClickListener{
            var params = HashMap<String, String>()
            params["firstName"] = firstName
            params["email"] = email
            params["password"] = password
            params["mobile"] = mobile

            var jsonObjectRegister = JSONObject(params as Map<*, *>)

            var url = "https://grocery-second-app.herokuapp.com/api/auth/register"

            var request = JsonObjectRequest(Request.Method.POST, url, jsonObjectRegister,{
                Toast.makeText(applicationContext, "Registered Successfully!", Toast.LENGTH_SHORT).show()

            }, {
//                Log.d("Error Message", it.message)
            })
            Volley.newRequestQueue(this).add(request)
        }

        button_login.setOnClickListener{
            var url = "https://grocery-second-app.herokuapp.com/api/auth/login"
            var paramsLogin = HashMap<String, String>()
            paramsLogin["email"] = email
            paramsLogin["password"] = password

            var jsonObjectLogin = JSONObject(paramsLogin as Map<*, *>)
            var request =  JsonObjectRequest(Request.Method.POST, url, jsonObjectLogin, {
                startActivity(Intent(this, MainActivity::class.java))
            }, {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
            })
            Volley.newRequestQueue(this).add(request)
        }

    }
}