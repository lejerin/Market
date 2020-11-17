package com.example.shopping.data.model

import com.google.gson.annotations.SerializedName

data class PurchaseRequest(

    @SerializedName("UID")
    val UID: String,

    @SerializedName("PID")
    val PID: String,

    @SerializedName("count")
    val count: Int,

    @SerializedName("mark")
    val mark: Int

)