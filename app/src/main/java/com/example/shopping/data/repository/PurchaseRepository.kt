package com.example.shopping.data.repository

import com.example.shopping.data.model.PurchaseRequest
import com.example.shopping.data.network.SafeApiRequest
import com.example.shopping.data.network.ShopApi
import okhttp3.MultipartBody
import okhttp3.RequestBody

class PurchaseRepository (
    private val api: ShopApi
) : SafeApiRequest(){


    suspend fun getProductWithId(
        id: Int
    )
        = apiRequest {
        api.getProductWithId(id)
    }

    suspend fun purchaseProduct(
      purchaseRequest: PurchaseRequest
    )
            = apiRequest {
        api.purchaceProduct(purchaseRequest)
    }

}