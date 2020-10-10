package com.example.shopping.data.repository

import com.example.shopping.data.model.LoginRequest
import com.example.shopping.data.model.SignUpRequest
import com.example.shopping.data.network.SafeApiRequest
import com.example.shopping.data.network.ShopApi
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ApiRepository (
    private val api: ShopApi
) : SafeApiRequest(){

    suspend fun login(id: String, data: LoginRequest)
            = apiRequest {
        api.signInUser(id, data)
    }

    suspend fun signUp(data: SignUpRequest)
            = apiRequest {
        api.signUpUser(data)
    }

    suspend fun getProduct()
            = apiRequest {
        api.getProduct()
    }



}