package com.ravimishra.tradzhub.Model

import com.google.firebase.database.Exclude
import java.io.Serializable

data class Product(
        val id: Long,
        var name: String,
        var price: Int,
        var discount: Int,
        var imgUrl: String,
        var desc: String,
        var wishlist: String,
        var cart: String,
        var category: String
        ):Serializable{

        @Exclude
        fun toMap(): Map<String, Any?> {
                return mapOf(
                        "id" to id,
                        "name" to name,
                        "price" to price,
                        "discount" to discount,
                        "imgUrl" to imgUrl,
                        "desc" to desc,
                        "wishlist" to wishlist,
                        "cart" to cart,
                        "category" to category
                )
        }
}