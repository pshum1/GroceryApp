package com.example.groceryapp.helpers

import android.content.Context
import com.example.groceryapp.models.Address

class AddressSessionManager (private var context: Context){

    private val FILE_NAME = "address_pref"

    var sharePreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    var editor = sharePreferences.edit()

    fun saveAddressSelection(address: Address){
        editor.putInt(Address.KEY_PINCODE, address.pincode)
        editor.putString(Address.KEY_HOUSE_NO, address.houseNo)
        editor.putString(Address.KEY_STREET_NAME, address.streetName)
        editor.putString(Address.KEY_CITY, address.city)
        editor.commit()
    }

    fun getAddress(): Address{
        var pincode = sharePreferences.getInt(Address.KEY_PINCODE, 0)
        var houseNo = sharePreferences.getString(Address.KEY_HOUSE_NO, null)
        var streetName = sharePreferences.getString(Address.KEY_STREET_NAME, null)
        var city = sharePreferences.getString(Address.KEY_CITY, null)

        return Address(pincode = pincode, houseNo = houseNo.toString(), streetName = streetName.toString(), city = city.toString())
    }

    fun deleteAddress(){
        editor.clear()
        editor.commit()
    }

}