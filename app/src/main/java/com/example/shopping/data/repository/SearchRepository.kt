package com.example.shopping.data.repository

import com.example.shopping.data.network.SafeApiRequest
import com.example.shopping.data.network.ShopApi
import okhttp3.MultipartBody
import okhttp3.RequestBody

class SearchRepository (
    private val api: ShopApi
) : SafeApiRequest(){


    suspend fun searchProduct(
        search_name: String
    )
            = apiRequest {
        api.getSearchProduct(search_name)
    }

}