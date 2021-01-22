package com.ravimishra.tradzhub.Model

import java.io.Serializable


data class Store(
        val id: Long,
        var name: String,
        var ImgUrl: String,
        var storeDesc: String
        ): Serializable