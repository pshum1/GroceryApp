package com.example.groceryapp.models

data class AddressResponse(
    val count: Int,
    val `data`: ArrayList<Address>,
    val error: Boolean
)

data class Address(
    val __v: Int? = null,
    val _id: String? = null,
    val city: String,
    val houseNo: String,
    val pincode: Int,
    val streetName: String,
    val type: String? = null,
    val userId: String? = null
) {
    companion object {
        const val KEY_CITY = "city"
        const val KEY_HOUSE_NO = "houseNo"
        const val KEY_PINCODE = "pincode"
        const val KEY_STREET_NAME = "streetName"
        const val KEY_TYPE = "type"
        const val KEY_USER_ID = "userId"
    }
}
