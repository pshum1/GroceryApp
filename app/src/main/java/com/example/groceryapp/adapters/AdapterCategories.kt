package com.example.groceryapp.adapters

import android.content.Context
import android.content.Intent
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.activities.SubCategoryActivity
import com.example.groceryapp.app.Config
import com.example.groceryapp.models.CategoriesData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_view_recycler_landing_page.view.*

class AdapterCategories(private var context: Context, private var list: ArrayList<CategoriesData>) :
    RecyclerView.Adapter<AdapterCategories.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context)
            .inflate(R.layout.row_view_recycler_landing_page, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var categoryData: CategoriesData = list[position]
        holder.bind(categoryData)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData() {
        //list = l
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(categoryData: CategoriesData) {
            itemView.tv_category_name.text = categoryData.catName
            var imgLink = Config.IMAGE_URL + categoryData.catImage

            Picasso.get().load(imgLink).error(R.drawable.ic_baseline_broken_image_24).into(itemView.img_view_categories)

            itemView.setOnClickListener {
                context.startActivity(Intent(context, SubCategoryActivity::class.java).apply {
                    putExtra(CategoriesData.KEY_CATEGORY, categoryData)
                })
            }
        }
    }
}