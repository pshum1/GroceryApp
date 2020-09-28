package com.example.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.models.User
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        init()
    }

    private fun init() {
        //set on click listeners

        //GO BACK TO LOGIN ACTIVITY
        button_login_from_register.setOnClickListener{
            finish()
        }

        button_register_from_register.setOnClickListener{
            var params = HashMap<String, String>()

            val name = et_register_first_name.text.toString()
            val email = et_register_email.text.toString()
            val mobile = et_register_mobile.text.toString()
            val password = et_register_password.text.toString()

            params[User.KEY_FIRST_NAME] = name
            params[User.KEY_EMAIL] = email
            params[User.KEY_MOBILE] = mobile
            params[User.KEY_PASSWORD] = password

            var jsonObjectRegister = JSONObject(params as Map<*, *>)

            var request = JsonObjectRequest(Request.Method.POST, Endpoints.getRegister(), jsonObjectRegister,{
                Toast.makeText(applicationContext, "Registered Successfully!", Toast.LENGTH_SHORT).show()
                finish()
            }, {
                Toast.makeText(applicationContext, "Error in registration", Toast.LENGTH_SHORT).show()
            })
            Volley.newRequestQueue(this).add(request)

        }


    }


}