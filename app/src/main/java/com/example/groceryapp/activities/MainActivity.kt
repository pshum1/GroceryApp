package com.example.groceryapp.activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterCategories
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.helpers.SessionManager
import com.example.groceryapp.models.CategoriesData
import com.example.groceryapp.models.CategoriesResult
import com.example.groceryapp.models.User
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header.view.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    //Layouts for drawer
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    lateinit var user: User

    //Categories RecyclerView
    private var mList: ArrayList<CategoriesData> = ArrayList()
    private var adapterCategories: AdapterCategories? = null

    //Session manager
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionManager = SessionManager(this)

        init()
    }

    // INITIALIZE
    private fun init() {
        //SETUP DRAWER AND TOOLBAR
        setupToolbar()
        setupDrawer()

        img_view_home_image.setImageResource(R.drawable.grocery_landing)

        getData()

        adapterCategories = AdapterCategories(this, mList)
        //recycler_view_landing_page.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_view_landing_page.layoutManager = GridLayoutManager(this, 2)
        recycler_view_landing_page.adapter = adapterCategories

    }

    //SETUP DRAWER
    private fun setupDrawer() {
        drawerLayout = drawer_layout
        navView = nav_view

        user = if(sessionManager.isLoggedIn()){
            sessionManager.getUser()
        } else {
            User(firstName = "Guest", email = "")
        }

        var headerView = navView.getHeaderView(0)
        headerView.tv_header_name.text = user.firstName
        headerView.tv_header_email.text = user.email

        var toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    //SETUP MENU AND TOOLBAR
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
        }
        return true
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_account -> Toast.makeText(this, "Account selected", Toast.LENGTH_SHORT).show()
            R.id.item_settings -> Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
                .show()
            R.id.item_orders -> Toast.makeText(this, "Orders selected", Toast.LENGTH_SHORT).show()
            R.id.item_Logout -> dialogueLogout()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun dialogueLogout() {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to logout?")
        builder.setPositiveButton("Yes", object :DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                sessionManager.logout()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }

        })
        builder.setNegativeButton(
            "No"
//            object: DialogInterface.OnClickListener{
//                override fun onClick(dialogue: DialogInterface?, p1: Int) {
//                    dialogue?.dismiss()
//                }
        )
        { dialogue, p1 -> dialogue?.dismiss() }
        var alertDialogue = builder.create()
        alertDialogue.show()

    }

    //GET DATA
    private fun getData(){

        var request = StringRequest(Request.Method.GET, Endpoints.getCategory(), {
//            var gson = Gson()
            var categoriesResult = Gson().fromJson(it, CategoriesResult::class.java)

            mList.addAll(categoriesResult.data)
            adapterCategories?.setData()
            progress_bar_landing?.visibility = View.GONE
        }, {
            //Log.d("abc", it.message)
        })

        Volley.newRequestQueue(this).add(request)
    }

}