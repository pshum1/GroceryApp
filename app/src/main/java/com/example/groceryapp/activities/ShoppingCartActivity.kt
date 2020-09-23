package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterCart
import com.example.groceryapp.adapters.AdapterProducts
import com.example.groceryapp.databases.DBHelper
import com.example.groceryapp.models.Products
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_shopping_cart.*
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

    //SETUP MENU AND TOOLBAR
    private fun setupToolbar(){
        var toolbar = toolbar
        toolbar.title = "Grocery App"
        setSupportActionBar(toolbar)
    }

    private fun init() {
        setupToolbar()
        mList = dbHelper.getProduct()
        Toast.makeText(applicationContext, "All Products Retrieved", Toast.LENGTH_SHORT).show()

        adapterCart = AdapterCart(this, mList)
        recycler_view_cart.layoutManager = LinearLayoutManager(this)
        recycler_view_cart.adapter = adapterCart
    }
}