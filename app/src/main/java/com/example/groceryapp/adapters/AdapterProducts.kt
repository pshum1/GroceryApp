package com.example.groceryapp.adapters

import android.content.Context
import android.content.Intent
import android.text.Layout
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.activities.ProductDetailActivity
import com.example.groceryapp.activities.SubCategoryActivity
import com.example.groceryapp.models.CategoriesData
import com.example.groceryapp.models.ProductData
import com.example.groceryapp.models.SubCatData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_view_recycler_cart.view.*
import kotlinx.android.synthetic.main.row_view_recycler_landing_page.view.*
import kotlinx.android.synthetic.main.row_view_recycler_product.view.*
import kotlinx.android.synthetic.main.row_view_recycler_product.view.img_view_product

class AdapterProducts(private var context: Context, private var list: ArrayList<ProductData>) :
    RecyclerView.Adapter<AdapterProducts.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =
            LayoutInflater.from(context).inflate(R.layout.row_view_recycler_product, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var products: ProductData = list[position]
        holder.bind(products)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData() {
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(products: ProductData) {
            //SET DATA INTO VIEWS
            itemView.tv_product_name.text = products.productName
            itemView.tv_product_price.text = "$${products.price}"
            var imgLink = "https://rjtmobile.com/grocery/images/${products.image}"

            Picasso.get().load(imgLink).error(R.drawable.ic_baseline_broken_image_24)
                .into(itemView.img_view_product)

            //STRIKETHROUGH FOR MRP
            val str = "$${products.mrp}"
            val spannableString = SpannableString(str)
            spannableString.setSpan(StrikethroughSpan(), 0, str.length,0)
            itemView.tv_product_mrp.text = spannableString

            //ONCLICK
            itemView.setOnClickListener {
                context.startActivity(Intent(context, ProductDetailActivity::class.java).apply {
                    putExtra(ProductData.KEY_PRODUCT, products)
//                    putExtra("PNAME", products.productName)
//                    putExtra("PPRICE", products.price.toString())
//                    putExtra("IMGLINK", imgLink)
                })
            }
        }
    }
}