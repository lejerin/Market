package com.example.shopping.data.model

import com.google.gson.annotations.SerializedName

data class UploadRequest(

    @SerializedName("product_name")
    val product_name : String,

    @SerializedName("product_detail")
    val product_detail : String,

    @SerializedName("product_price")
    val product_price : Int,

    @SerializedName("product_stock")
    val product_stock : Int,

    @SerializedName("product_major_category")
    val product_major_category : String,

    @SerializedName("product_minor_category")
    val product_minor_category : String,

    @SerializedName("product_mark")
    val product_mark : Float,

    @SerializedName("product_merchandiser")
    val product_merchandiser : String
)