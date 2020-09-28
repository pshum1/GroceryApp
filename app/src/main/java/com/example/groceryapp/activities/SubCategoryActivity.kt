package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuItemCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterTabViewPager
import com.example.groceryapp.databases.DBHelper
import com.example.groceryapp.models.CategoriesData
import com.example.groceryapp.models.CategoriesResult
import com.example.groceryapp.models.SubCatData
import com.example.groceryapp.models.SubCategoryResult
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.fragment_product.*
import kotlinx.android.synthetic.main.layout_menu_cart.view.*

class SubCategoryActivity : AppCompatActivity() {

    var category: CategoriesData? = null
    var mList: ArrayList<SubCatData> = ArrayList()
    var subCatAdapter: AdapterTabViewPager? = null

    var textViewCartCount: TextView? = null
    lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)

        category = intent.getSerializableExtra(CategoriesData.KEY_CATEGORY) as CategoriesData
        dbHelper = DBHelper(this)

        var url = "https://grocery-second-app.herokuapp.com/api/subcategory/${category?.catId}"

        init(url)
    }

    override fun onRestart() {
        super.onRestart()
        updateCartCount()
    }

    private fun init(url:String) {
        setupToolBar()
        Log.d("abc", "SUBCATACTIVITY INIT")
        getData(url)
        subCatAdapter = AdapterTabViewPager(supportFragmentManager)
        view_pager.adapter = subCatAdapter
        tab_layout.setupWithViewPager(view_pager)

    }

    private fun setupToolBar(){
        var toolbar = toolbar
        toolbar.title = category!!.catName
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cart, menu)

        var item = menu.findItem(R.id.action_cart_custom)
        MenuItemCompat.setActionView(item, R.layout.layout_menu_cart)
        var view = MenuItemCompat.getActionView(item)
        textViewCartCount = view.text_view_cart_count
        updateCartCount()

        view.setOnClickListener {
            startActivity(Intent(this, ShoppingCartActivity::class.java))
        }
        return true
    }

    private fun updateCartCount(){
        var count = dbHelper.getQuantityTotal()
        if(count == 0){
            textViewCartCount?.visibility = View.GONE
        } else {
            textViewCartCount?.visibility = View.VISIBLE
            textViewCartCount?.text = count.toString()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            //R.id.action_cart -> startActivity(Intent(this, ShoppingCartActivity::class.java))
        }
        return true
    }

    private fun getData(url: String) {

        var request = StringRequest(Request.Method.GET, url, {
            var gson = Gson()
            var subCatResult = gson.fromJson(it, SubCategoryResult::class.java)

            Log.d("abc", url)
            mList.addAll(subCatResult.data)

            for(i in 0 until mList.size){
                subCatAdapter?.addFragment(mList[i].subName, mList[i].subId)
            }
            subCatAdapter?.dataChanged()

        }, {
            //Log.d("abc", it.message)
        })

        Volley.newRequestQueue(this).add(request)
    }
}