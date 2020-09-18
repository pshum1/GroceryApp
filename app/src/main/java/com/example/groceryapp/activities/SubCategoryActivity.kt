package com.example.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterTabViewPager
import com.example.groceryapp.models.CategoriesResult
import com.example.groceryapp.models.SubCatData
import com.example.groceryapp.models.SubCategoryResult
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.fragment_product.*

class SubCategoryActivity : AppCompatActivity() {

    var mList: ArrayList<SubCatData> = ArrayList()
    var subCatAdapter: AdapterTabViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)

        var catId = intent.getIntExtra("CATID", 1).toString()

        var url = "https://grocery-second-app.herokuapp.com/api/subcategory/$catId"

        init(url)
    }

    private fun init(url:String) {
        Log.d("abc", "SUBCATACTIVITY INIT")
        getData(url)
        subCatAdapter = AdapterTabViewPager(supportFragmentManager)
        view_pager.adapter = subCatAdapter
        tab_layout.setupWithViewPager(view_pager)

    }

    private fun getData(url: String) {

        var request = StringRequest(Request.Method.GET, url, {
            var gson = Gson()
            var subCatResult = gson.fromJson(it, SubCategoryResult::class.java)

            Log.d("abc", url)
            mList.addAll(subCatResult.data)
            //Log.d("abc", mList[0].subName)
            for(i in 0 until mList.size){
                subCatAdapter?.addFragment(mList[i].subName, mList[i].subId)
            }
            subCatAdapter?.dataChanged()

        }, {
            Log.d("abc", it.message)
        })

        Volley.newRequestQueue(this).add(request)
    }
}