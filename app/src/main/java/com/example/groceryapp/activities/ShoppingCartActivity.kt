package com.example.groceryapp.activities

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterCart
import com.example.groceryapp.adapters.AdapterProducts
import com.example.groceryapp.databases.DBHelper
import com.example.groceryapp.models.Products
import com.example.groceryapp.models.Totals
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_shopping_cart.*
import kotlinx.android.synthetic.main.activity_shopping_cart.view.*
import kotlinx.android.synthetic.main.app_bar.*

class ShoppingCartActivity : AppCompatActivity() {

    var mList: ArrayList<Products> = ArrayList()
    private var adapterCart: AdapterCart? = null
    lateinit var dbHelper: DBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)

        dbHelper = DBHelper(this)
        init()
    }

    private fun init() {

        mList = dbHelper.getProduct()

        if(mList.isNullOrEmpty()){
            recycler_view_cart.visibility = View.GONE
        } else {
            tv_no_cart_items.visibility = View.GONE
        }
        Toast.makeText(applicationContext, "All Products Retrieved", Toast.LENGTH_SHORT).show()
        var totals = calculateTotals(mList)

        tv_subtotal_amount.text = """${"$"}${totals.subtotal}"""
        tv_discount_amount.text = "-$" + totals.discount.toString()
        tv_total_amount.text = "$" + totals.total.toString()

        adapterCart = AdapterCart(this, mList)
        recycler_view_cart.layoutManager = LinearLayoutManager(this)
        recycler_view_cart.adapter = adapterCart

        button_checkout.setOnClickListener{
            startActivity(Intent(this, AddressActivity::class.java))
        }
    }

    private fun calculateTotals(list: ArrayList<Products>): Totals {
        var subtotal = 0.0
        var discount = 0.0
        var total: Double
        for(i in 0 until list.size){
            subtotal += list[i].mrp * list[i].quantity
            discount += (list[i].mrp - list[i].price) * list[i].quantity
        }
        total = subtotal-discount
        Log.d("totals", "$subtotal sub $discount dis $total total")
        return Totals(subtotal,discount, total)
    }
}