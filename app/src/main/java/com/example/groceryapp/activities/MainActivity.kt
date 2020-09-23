package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterCategories
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.models.CategoriesData
import com.example.groceryapp.models.CategoriesResult
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private var mList: ArrayList<CategoriesData> = ArrayList()
    private var adapterCategories: AdapterCategories? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    //SETUP MENU AND TOOLBAR
    private fun setupToolbar(){
        var toolbar = toolbar
        toolbar.title = "Grocery App"
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_cart -> startActivity(Intent(this, ShoppingCartActivity::class.java))
            R.id.action_profile -> Toast.makeText(applicationContext, "Profile Action", Toast.LENGTH_SHORT).show()
            R.id.action_setting -> Toast.makeText(applicationContext, "Setting Action", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    // INITIALIZE
    private fun init() {
        setupToolbar()
        img_view_home_image.setImageResource(R.drawable.grocery_landing)

        getData()

        adapterCategories = AdapterCategories(this, mList)
        //recycler_view_landing_page.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_view_landing_page.layoutManager = GridLayoutManager(this, 2)
        recycler_view_landing_page.adapter = adapterCategories
    }

    //GET DATA
    private fun getData(){

        var request = StringRequest(Request.Method.GET, Endpoints.getCategory(), {
            var gson = Gson()
            var categoriesResult = gson.fromJson(it, CategoriesResult::class.java)

            mList.addAll(categoriesResult.data)
            adapterCategories?.setData()
            progress_bar_landing.visibility = View.GONE
        }, {
            //Log.d("abc", it.message)
        })

        Volley.newRequestQueue(this).add(request)
    }


}