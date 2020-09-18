package com.example.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.groceryapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.row_view_recycler_product.view.*

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        tv_product_detail_name.text = intent.getStringExtra("PNAME")
        tv_product_detail_price.text = intent.getStringExtra("PPRICE")

        var imgLink = intent.getStringExtra("IMGLINK")
        Picasso.get().load(imgLink).error(R.drawable.ic_launcher_background)
            .into(img_view_product_detail)
    }
}