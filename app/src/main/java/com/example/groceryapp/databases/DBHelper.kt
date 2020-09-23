package com.example.groceryapp.databases

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.groceryapp.models.Products

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    var dbHelper = writableDatabase

    companion object {
        const val DATABASE_NAME = "productDB"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "product"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_IMAGE = "image"
        const val COLUMN_MRP = "mrp"
        const val COLUMN_PRICE = "price"
        const val COLUMN_QUANTITY = "quantity"

        const val createTable =
            "create table $TABLE_NAME ( $COLUMN_ID CHAR(50), $COLUMN_NAME CHAR(50), $COLUMN_IMAGE CHAR(50), $COLUMN_MRP DECIMAL, $COLUMN_PRICE DECIMAL, $COLUMN_QUANTITY INTEGER)"

        const val dropTable = "drop table $TABLE_NAME"
    }

    override fun onCreate(database: SQLiteDatabase?) {
        database?.execSQL(createTable)
        Log.d("abc", "OnCreate DB")
    }

    override fun onUpgrade(database: SQLiteDatabase?, p1: Int, p2: Int) {
        database?.execSQL(dropTable)
        onCreate(database)
    }

    //add product to database
    fun addProduct(products: Products){
        var contentValues = ContentValues()
        contentValues.put(COLUMN_ID, products._id)
        contentValues.put(COLUMN_NAME, products.productName)
        contentValues.put(COLUMN_IMAGE, products.img)
        contentValues.put(COLUMN_MRP, products.mrp)
        contentValues.put(COLUMN_PRICE, products.price)
        contentValues.put(COLUMN_QUANTITY, products.quantity)

        dbHelper.insert(TABLE_NAME, null, contentValues)
    }

    fun updateQuantityProduct(products: Products, quantity: Int){
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(products._id)

        var newQuantity = products.quantity + quantity

        val contentValues = ContentValues()
        contentValues.put(COLUMN_QUANTITY, newQuantity)

        dbHelper.update(TABLE_NAME, contentValues, whereClause, whereArgs)
    }

    fun checkIfRecordExists(id: String): Boolean{
        val columns = arrayOf(COLUMN_ID)

        Log.d("recordExistence", "It reached record check")
        var cursor= dbHelper.query(TABLE_NAME, columns, null, null, null, null, null)

        if(cursor != null && cursor.moveToFirst()) {
            do {
                var idDB = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                if (idDB == id) {
                    Log.d("recordExistence", true.toString())
                    cursor.close()
                    return true
                }
            } while (cursor.moveToNext())
        }
        Log.d("recordExistence", false.toString())
        cursor.close()
        return false

    }

    fun deleteProduct(id: String){
        var whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(id)

        dbHelper.delete(TABLE_NAME, whereClause, whereArgs)
    }

    fun getProduct(): ArrayList<Products>{
        var productList: ArrayList<Products> = ArrayList()
        var columns = arrayOf(
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_IMAGE,
            COLUMN_MRP,
            COLUMN_PRICE,
            COLUMN_QUANTITY,
        )
        var cursor = dbHelper.query(TABLE_NAME, columns, null, null, null, null, null)
        if(cursor != null && cursor.moveToFirst()) {
            do {
                var id = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                var name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                var img = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
                var mrp = cursor.getDouble(cursor.getColumnIndex(COLUMN_MRP))
                var price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE))
                var quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY))

                var product = Products(id,name, img, mrp, price, quantity)
                productList.add(product)

            } while (cursor.moveToNext())
        }
        cursor.close()
        return productList
    }


}