package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterCategories
import com.example.groceryapp.models.CategoriesData
import com.example.groceryapp.models.CategoriesResult
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    var mList: ArrayList<CategoriesData> = ArrayList()
    var adapterCategories: AdapterCategories? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {

        getData()

        adapterCategories = AdapterCategories(this, mList)
        recycler_view_landing_page.layoutManager = GridLayoutManager(this,2)
        recycler_view_landing_page.adapter = adapterCategories
    }

    private fun getData(){
        var url = "https://grocery-second-app.herokuapp.com/api/category"

        var request = StringRequest(Request.Method.GET, url, {
            var gson = Gson()
            var categoriesResult = gson.fromJson(it, CategoriesResult::class.java)

            mList.addAll(categoriesResult.data)
            adapterCategories?.setData()
            progress_bar_landing.visibility = View.GONE
        }, {
            Log.d("abc", it.message)
        })

        Volley.newRequestQueue(this).add(request)
    }


}