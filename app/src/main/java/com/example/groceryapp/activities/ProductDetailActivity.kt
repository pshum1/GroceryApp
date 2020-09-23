package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.groceryapp.R
import com.example.groceryapp.app.Config
import com.example.groceryapp.databases.DBHelper
import com.example.groceryapp.models.ProductData
import com.example.groceryapp.models.Products
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.row_view_recycler_product.view.*

class ProductDetailActivity : AppCompatActivity() {

    //DECLARE GLOBAL VARIABLES

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        var products = intent.getSerializableExtra(ProductData.KEY_PRODUCT) as ProductData

        //INITIALIZE DATABASE HELPER
        dbHelper = DBHelper(this)

        init(products)
    }

    private fun setupToolbar() {
        var toolbar = toolbar
        toolbar.title = "Grocery App"
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_cart -> startActivity(Intent(this, ShoppingCartActivity::class.java))
            R.id.action_profile -> Toast.makeText(
                applicationContext,
                "Profile Action",
                Toast.LENGTH_SHORT
            ).show()
            R.id.action_setting -> Toast.makeText(
                applicationContext,
                "Setting Action",
                Toast.LENGTH_SHORT
            ).show()
        }
        return true
    }

    private fun init(products: ProductData) {
        setupToolbar()
        //SET DATA INTO VIEWS
        tv_product_detail_name.text = products.productName
        tv_product_detail_price.text = "$${products.price}"
        tv_product_detail_desc.text = products.description

        var imgLink = Config.IMAGE_URL + products.image
        Picasso.get().load(imgLink).error(R.drawable.ic_baseline_broken_image_24)
            .into(img_view_product_detail)

        //DECLARE AND INITIALIZE QUANTITY
        var quantity = tv_product_detail_quantity.text.toString().toInt()

        button_product_add.setOnClickListener {
            var productsDB = Products(
                products._id,
                products.productName,
                products.image,
                products.mrp,
                products.price,
                quantity)

            Log.d("quantity", productsDB.quantity.toString() + " detail")
            if(dbHelper.checkIfRecordExists(productsDB._id)){
                dbHelper.updateQuantityProduct(productsDB, quantity)
            }else {
                dbHelper.addProduct(productsDB)
            }
            startActivity(Intent(this, ShoppingCartActivity::class.java))
            finish()
        }

        button_quantity_add.setOnClickListener {

            quantity++
            tv_product_detail_quantity.text = quantity.toString()
        }

        button_quantity_subtract.setOnClickListener {
            if (quantity > 1) {
                quantity--
                tv_product_detail_quantity.text = quantity.toString()
            }
        }

    }
}