package com.example.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.app.Endpoints
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.activity_add_address.view.*
import org.json.JSONObject

class AddAddressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)

        init()
    }

    private fun init() {

        button_save_address.setOnClickListener{
            var addressParams = HashMap<String, String>()

            addressParams["name"] = et_address_name.text.toString()
            addressParams["mobile"] = et_address_number.text.toString()
            addressParams["houseNo"] = et_address_house_number.text.toString()
            addressParams["streetName"] = et_address_street_name.text.toString()
            addressParams["location"] = et_address_location.text.toString()
            addressParams["pincode"] = et_address_pincode.text.toString()
            addressParams["city"] = et_address_city.text.toString()

            var jsonObjectRegister = JSONObject(addressParams as Map<*, *>)
            var url = Endpoints.postAddress()

            var request = JsonObjectRequest(Request.Method.POST, url, jsonObjectRegister, {
                Toast.makeText(applicationContext, "Registered Successfully!", Toast.LENGTH_SHORT).show()
                finish()
            }, {

            })
            Volley.newRequestQueue(this).add(request)
        }
    }
}