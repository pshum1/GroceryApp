package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.se.omapi.Session
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.helpers.SessionManager
import com.example.groceryapp.models.OrderHistoryData
import com.example.groceryapp.models.OrderHistoryResult
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_order_history.*
import kotlinx.android.synthetic.main.activity_order_history_detail.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.row_view_recycler_order_history.view.*

class OrderHistoryDetailActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager
    private var mList: ArrayList<OrderHistoryData> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history_detail)

        var position = intent.getIntExtra("position", 0)

        sessionManager = SessionManager(this)

        init(position)
    }

    private fun setupToolBar(){
        var toolbar = toolbar
        toolbar.title = "Order Detail"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return true
    }
    private fun init(position: Int) {
        setupToolBar()
        getData(position)

    }

    private fun getData(position: Int) {
        var url = "${Endpoints.getOrder()}/${sessionManager.getUserId()}"

        var request = StringRequest(Request.Method.GET, url, {

            var orderResult = Gson().fromJson(it, OrderHistoryResult::class.java)

            mList.addAll(orderResult.data)
            //progress_bar_order_history.visibility = View.GONE
            tv_order_total.text = "$" + mList[position].orderSummary.totalAmount.toString()
            tv_subtotal_amount.text = "$" + mList[position].orderSummary.ourPrice.toString()
            tv_total_amount.text = "$" + mList[position].orderSummary.totalAmount.toString()
            tv_discount_amount.text = "$" + ((mList[position].orderSummary.ourPrice) - (mList[position].orderSummary.totalAmount)).toString()
            tv_delivery_amount.text = "$" + mList[position].orderSummary.deliveryCharges.toString()

            tv_house_no_street_name.text = "${mList[position].shippingAddress.houseNo}, ${mList[position].shippingAddress.streetName}"
            tv_address_city_pincode.text = "${mList[position].shippingAddress.city}, ${mList[position].shippingAddress.pincode}"

            tv_history_date.text = mList[position].date


        }, {
            Toast.makeText(applicationContext, "Error Loading Data", Toast.LENGTH_SHORT).show()
        })

        Volley.newRequestQueue(this).add(request)
    }
}