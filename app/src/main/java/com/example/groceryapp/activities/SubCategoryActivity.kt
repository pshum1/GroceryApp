package com.example.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterTabViewPager
import com.example.groceryapp.models.CategoriesData
import com.example.groceryapp.models.CategoriesResult
import com.example.groceryapp.models.SubCatData
import com.example.groceryapp.models.SubCategoryResult
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.fragment_product.*

class SubCategoryActivity : AppCompatActivity() {


    var category: CategoriesData? = null
    var mList: ArrayList<SubCatData> = ArrayList()
    var subCatAdapter: AdapterTabViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)

        category = intent.getSerializableExtra(CategoriesData.KEY_CATEGORY) as CategoriesData

        var url = "https://grocery-second-app.herokuapp.com/api/subcategory/${category?.catId}"

        init(url)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            R.id.action_cart -> Toast.makeText(applicationContext, "Cart Action", Toast.LENGTH_SHORT).show()
            R.id.action_profile -> Toast.makeText(applicationContext, "Profile Action", Toast.LENGTH_SHORT).show()
            R.id.action_setting -> Toast.makeText(applicationContext, "Setting Action", Toast.LENGTH_SHORT).show()
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