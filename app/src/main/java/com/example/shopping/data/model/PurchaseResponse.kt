package com.example.shopping.data.model

import com.google.gson.annotations.SerializedName

data class PurchaseResponse(
    @SerializedName("message")
    val message : String
    )