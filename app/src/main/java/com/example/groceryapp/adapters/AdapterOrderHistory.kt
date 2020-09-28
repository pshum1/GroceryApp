package com.example.groceryapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.activities.OrderHistoryDetailActivity
import com.example.groceryapp.models.Address

import com.example.groceryapp.models.OrderHistoryData
import kotlinx.android.synthetic.main.row_view_recycler_order_history.view.*

class AdapterOrderHistory(private var context: Context, private var list: ArrayList<OrderHistoryData>) :
    RecyclerView.Adapter<AdapterOrderHistory.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context)
            .inflate(R.layout.row_view_recycler_order_history, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var orderHistory: OrderHistoryData = list[position]
        holder.bind(orderHistory, position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(l: ArrayList<OrderHistoryData>){
        list = l
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(orderHistory: OrderHistoryData, position: Int) {
            itemView.tv_history_date.text = orderHistory.date
            itemView.tv_history_totalAmount.text = "Total: $${orderHistory.orderSummary.totalAmount}"

            itemView.tv_house_no_street_name.text = "${orderHistory.shippingAddress.houseNo}, ${orderHistory.shippingAddress.streetName}"
            itemView.tv_address_city_pincode.text = "${orderHistory.shippingAddress.city}, ${orderHistory.shippingAddress.pincode}"

            itemView.setOnClickListener {
                context.startActivity(Intent(context, OrderHistoryDetailActivity::class.java).apply {
                    putExtra("position", position)
                })
            }
        }
    }
}