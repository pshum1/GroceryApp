package com.example.groceryapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.activities.OrderActivity
import com.example.groceryapp.helpers.AddressSessionManager
import com.example.groceryapp.helpers.SessionManager
import com.example.groceryapp.models.Address
import kotlinx.android.synthetic.main.row_view_recycler_address.view.*

class AdapterAddress(private var context: Context, private var list: ArrayList<Address>): RecyclerView.Adapter<AdapterAddress.ViewHolder>(){

    lateinit var addressSessionManager: AddressSessionManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.row_view_recycler_address, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var address: Address = list[position]
        holder.bind(address)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(l: ArrayList<Address>){
        list = l
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(address: Address){
            itemView.tv_house_no_street_name.text = "${address.houseNo}, ${address.streetName}"
            itemView.tv_address_city_pincode.text = "${address.city}, ${address.pincode}"
            itemView.tv_address_type.text = address.type

            itemView.setOnClickListener{
                addressSessionManager = AddressSessionManager(context)
                addressSessionManager.deleteAddress()
                addressSessionManager.saveAddressSelection(Address(city = address.city, houseNo = address.houseNo, pincode = address.pincode, streetName = address.streetName))

                context.startActivity(Intent(context, OrderActivity::class.java ))
            }
        }
    }




}