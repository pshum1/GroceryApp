package com.example.groceryapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterProducts
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.models.ProductData
import com.example.groceryapp.models.ProductsResult
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_product.*
import kotlinx.android.synthetic.main.fragment_product.view.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ProductFragment : Fragment() {

    //DECLARE AND INITIALIZE VARIABLES
    private var tabTitle: String? = null
    private var subId: Int? = null

    var mList: ArrayList<ProductData> = ArrayList()
    var adapterProducts: AdapterProducts? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tabTitle = it.getString(ARG_PARAM1)
            subId = it.getInt(ARG_PARAM2)
        }
        getData(subId!!)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_product, container, false)

        view.progress_bar_product.visibility = View.GONE
        init(view)

        return view
    }

    private fun init(view: View) {
        adapterProducts = AdapterProducts(activity!!, mList)
        view.recycler_view_product.layoutManager = LinearLayoutManager(activity!!)
        view.recycler_view_product.adapter = adapterProducts
    }

    private fun getData(subId: Int) {
        var url = Endpoints.getProductBySubId(subId)

        var request = StringRequest(Request.Method.GET, url, {
            //var gson = Gson()
            var productsResult = Gson().fromJson(it, ProductsResult::class.java)

            mList.addAll(productsResult.data)
            adapterProducts?.setData()


        }, {
            //Log.d("abc", it.message)
        })

        Volley.newRequestQueue(activity!!).add(request)
    }

    companion object {
        @JvmStatic
        fun newInstance(tabTitle: String, subId: Int) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, tabTitle)
                    putInt(ARG_PARAM2, subId)

                }
            }
    }
}