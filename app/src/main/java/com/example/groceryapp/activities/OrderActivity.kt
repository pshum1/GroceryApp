package com.example.groceryapp.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.databases.DBHelper
import com.example.groceryapp.helpers.AddressSessionManager
import com.example.groceryapp.helpers.SessionManager
import com.example.groceryapp.models.Address
import com.example.groceryapp.models.OrderSummary
import com.example.groceryapp.models.User
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONObject
import java.time.LocalDateTime

class OrderActivity : AppCompatActivity() {

    lateinit var dbHelper: DBHelper
    lateinit var sessionManager: SessionManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        dbHelper = DBHelper(this)
        sessionManager = SessionManager(this)

        init()
    }

    private fun setupToolBar(){
        var toolbar = toolbar
        toolbar.title = "Order Summary"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init() {
        setupToolBar()

        val orderSummary = dbHelper.getOrderSummary()

        //SET DATA INTO VIEWS
        tv_order_total.text = "$${orderSummary.total}"
        tv_order_savings.text = "You will save $${orderSummary.discount} on this order"
        tv_subtotal_amount.text = "$${orderSummary.mrp}"
        tv_discount_amount.text = "-$${orderSummary.discount}"
        tv_delivery_amount.text = "$${orderSummary.delivery}"
        tv_total_amount.text = "$${orderSummary.total}"

        button_order_coupon.setOnClickListener {
            Toast.makeText(this, "Not available at this time", Toast.LENGTH_SHORT).show()
        }

        button_order_pay_online.setOnClickListener {
            Toast.makeText(this, "Not available at this time. Please choose other option", Toast.LENGTH_SHORT).show()
        }

        button_order_pay_cash.setOnClickListener {
            postOrder()

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun postOrder() {
        var orderParams = HashMap<String, Any>()

        orderParams[User.KEY_USER_ID] = sessionManager.getUserId()
        orderParams[OrderSummary.KEY_ORDER_STATUS] = "Confirmed"
        orderParams[OrderSummary.KEY_ORDER_SUMMARY] = getOrderSummary()
        orderParams[OrderSummary.KEY_USER] = getUser()
        orderParams[OrderSummary.KEY_SHIPPING_ADDRESS] = getShippingAddress()
        orderParams[OrderSummary.KEY_PAYMENT] = getPayment()
        orderParams[OrderSummary.KEY_PRODUCTS] = getProductsSummary()
        orderParams[OrderSummary.KEY_DATE] = LocalDateTime.now()

        var jsonObjectOrder = JSONObject(orderParams as Map<*, *>)

        var url = Endpoints.getOrder()

        var request = JsonObjectRequest(Request.Method.POST, url, jsonObjectOrder, {
//            Toast.makeText(applicationContext, "Order Posted Successfully!", Toast.LENGTH_SHORT).show()
            var intent = Intent(this, OrderCompletedActivity::class.java)
            dbHelper.clearDB()
            startActivity(intent)
        }, {
            Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
        })

        Volley.newRequestQueue(this).add(request)



    }

    //GETS DATA FOR ORDER SUMMARY
    private fun getOrderSummary(): JSONObject {
        var orderSummaryParams = HashMap<String, Any>()
        var orderSummary = dbHelper.getOrderSummary()

        orderSummaryParams[OrderSummary.KEY_TOTAL] = orderSummary.total.toString().toDouble()
        orderSummaryParams[OrderSummary.KEY_OUR_PRICE] = orderSummary.mrp.toString().toDouble()
        orderSummaryParams[OrderSummary.KEY_DISCOUNT] = orderSummary.discount.toString().toDouble()
        orderSummaryParams[OrderSummary.KEY_DELIVERY] = orderSummary.delivery
        orderSummaryParams[OrderSummary.KEY_ORDER_AMOUNT] = orderSummary.total + orderSummary.delivery

        return JSONObject(orderSummaryParams as Map<*, *>)
    }

    //GETS DATA FOR USER
    private fun getUser(): JSONObject {
        var orderUserParams = HashMap<String, Any>()
        var userSummary = sessionManager.getUser()

        orderUserParams[User.KEY_EMAIL] = userSummary.email.toString()
        orderUserParams[User.KEY_MOBILE] = userSummary.mobile.toString()
        orderUserParams[User.KEY_NAME] = userSummary.firstName.toString()

        return JSONObject(orderUserParams as Map<*, *>)
    }

    //GETS DATA FOR SHIPPING ADDRESS
    private fun getShippingAddress(): JSONObject {
        var orderShippingParams = HashMap<String, Any>()

        var addressSessionManager = AddressSessionManager(this)
        val address = addressSessionManager.getAddress()

        orderShippingParams[Address.KEY_PINCODE] = address.pincode
        orderShippingParams[Address.KEY_HOUSE_NO] = address.houseNo
        orderShippingParams[Address.KEY_STREET_NAME] = address.streetName
        orderShippingParams[Address.KEY_CITY] = address.city

        return JSONObject(orderShippingParams as Map<*, *>)
    }

    //GETS DATA FOR PAYMENT METHOD
    private fun getPayment(): JSONObject {
        var orderPaymentParams = HashMap<String, Any>()

        orderPaymentParams[OrderSummary.KEY_PAYMENT_MODE] = "cash"
        orderPaymentParams[OrderSummary.KEY_PAYMENT_STATUS] = "completed"
        return JSONObject(orderPaymentParams as Map<*, *>)
    }

    //GETS DATA FOR PRODUCTS
    private fun getProductsSummary(): ArrayList<JSONObject> {
        val productsList = dbHelper.getProduct()

        var viewOfJsonObject = ""

        var orderProductsList: ArrayList<JSONObject> = ArrayList()

        for(i in 0 until productsList.size){
            var orderProductsParams = HashMap<String, Any>()

            var image = productsList[i].img
            var productMrp = productsList[i].mrp
            var productPrice = productsList[i].price
            var productQuantity = productsList[i].quantity

            orderProductsParams[DBHelper.COLUMN_IMAGE] = image
            orderProductsParams[DBHelper.COLUMN_MRP] = productMrp
            orderProductsParams[DBHelper.COLUMN_PRICE] = productPrice
            orderProductsParams[DBHelper.COLUMN_QUANTITY] = productQuantity

            orderProductsList.add(JSONObject(orderProductsParams as Map<*, *>))

        }


        return orderProductsList
    }


}