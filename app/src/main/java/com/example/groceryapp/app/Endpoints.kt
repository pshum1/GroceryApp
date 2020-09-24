package com.example.groceryapp.app

class Endpoints {

    companion object {

        private const val URL_LOGIN = "auth/login"
        private const val URL_REGISTER = "auth/register"
        private const val URL_CATEGORY = "category"
        private const val URL_SUB_CATEGORY = "subcategory/"
        private const val URL_PRODUCT_BY_SUB = "products/sub/"
        private const val URL_ADDRESS = "address"

        fun getLogin(): String{
            return Config.BASE_URL + URL_LOGIN
        }

        fun getRegister(): String{
            return Config.BASE_URL + URL_REGISTER
        }

        fun getCategory(): String{
            return Config.BASE_URL + URL_CATEGORY
        }

        fun getSubCategoryByCatId(catId: Int): String{
            return Config.BASE_URL + URL_SUB_CATEGORY + catId
        }

        fun getProductBySubId(subId: Int): String{
            return "${Config.BASE_URL + URL_PRODUCT_BY_SUB}$subId"
        }

        fun postAddress(): String{
            return Config.BASE_URL + URL_ADDRESS
        }
    }
}