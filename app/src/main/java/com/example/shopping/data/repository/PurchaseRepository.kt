package com.example.shopping.data.repository

import com.example.shopping.data.network.SafeApiRequest
import com.example.shopping.data.network.ShopApi
import okhttp3.MultipartBody
import okhttp3.RequestBody

class PurchaseRepository (
    private val api: ShopApi
) : SafeApiRequest(){


    suspend fun purchaseProduct(
        UID: String,
        PID: Int,
        count: Int,
        mark: Float
    )
            = apiRequest {
        api.purchaceProduct(UID, PID, count, mark)
    }

}