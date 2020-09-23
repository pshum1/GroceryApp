package com.example.groceryapp.adapters

import android.content.Context
import android.content.Intent
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.activities.AddressActivity
import com.example.groceryapp.app.Config
import com.example.groceryapp.databases.DBHelper
import com.example.groceryapp.models.Products
import com.example.groceryapp.models.Totals
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_shopping_cart.view.*
import kotlinx.android.synthetic.main.row_view_recycler_cart.view.*

class AdapterCart(private var context: Context, private var list: ArrayList<Products>): RecyclerView.Adapter<AdapterCart.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(
            R.layout.row_view_recycler_cart,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var products: Products = list[position]
        holder.bind(products, position)
    }

    override fun getItemCount(): Int {
        return list.size
    }



    fun setData(l: ArrayList<Products>) {
        list = l
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(products: Products, position: Int){


            var quantity = products.quantity
            Log.d("quantity", quantity.toString())

            //SET VIEWS WITH DATA
            itemView.tv_cart_name.text = products.productName
            itemView.tv_cart_price.text = "$${products.price}"
            itemView.tv_cart_quantity.text = quantity.toString()

//            itemView.tv_subtotal_amount.text = totals.subtotal.toString()
//            itemView.tv_discount_amount.text = totals.discount.toString()
//            itemView.tv_total_amount.text = totals.total.toString()


            //STRIKETHROUGH FOR MRP
            val str = "$${products.mrp}"
            val spannableString = SpannableString(str)
            spannableString.setSpan(StrikethroughSpan(), 0, str.length,0)
            itemView.tv_cart_mrp.text = spannableString

            var imgURL = Config.IMAGE_URL + products.img

            Picasso.get().load(imgURL).error(R.drawable.ic_baseline_broken_image_24).into(itemView.img_view_product)

            //ONCLICK FUNCTIONS
            itemView.button_delete_cart.setOnClickListener {
                var dbHelper = DBHelper(context)
                dbHelper.deleteProduct(products._id)
                list.removeAt(position)
                setData(list)
            }

            itemView.button_quantity_add_cart.setOnClickListener{

            }


        }
    }

}