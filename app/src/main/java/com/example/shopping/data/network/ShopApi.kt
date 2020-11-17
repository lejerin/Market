package com.example.shopping.data.network

import com.example.shopping.data.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ShopApi {

    //회원가입
    @POST("account/signup")
    suspend fun signUpUser(
        @Body param : SignUpRequest
    ): Response<SignUpResponse>

    //로그인
    @POST("account/signin")
    suspend fun signInUser(
        @Body param : LoginRequest
    ): Response<LoginResponse>

    //상품 목록 불러오기
    @GET("product/")
    suspend fun getProduct(
        @Query("page") page: Int
    ): Response<ProductResponse>

    //상품 목록 id로 불러오기
    @GET("product/{path}")
    suspend fun getProductWithId(
        @Path("path") path: Int
    ): Response<Product>

    //검색 목록 불러오기
    @GET("product/")
    suspend fun getSearchProduct(
        @Query("search") part: String
    ): Response<ProductResponse>

    //구입
    @POST("order/purchase")
    suspend fun purchaceProduct(
        @Body param : PurchaseRequest
    ): Response<PurchaseResponse>


    //상품 목록 보내기
    @Multipart
    @POST("product/create")
    suspend fun uploadProduct(
        @Part file: MultipartBody.Part,
        @Part("product_name") product_name: RequestBody,
        @Part("product_detail") product_detail: RequestBody,
        @Part("product_price") product_price: RequestBody,
        @Part("product_stock") product_stock: RequestBody,
        @Part("product_major_category") product_major_category: RequestBody,
        @Part("product_minor_category") product_minor_category: RequestBody,
        @Part("product_merchandiser") product_merchandiser: RequestBody
    ): Response<UploadResponse>


    companion object{
        operator fun invoke() : ShopApi {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://ec2-54-180-20-247.ap-northeast-2.compute.amazonaws.com/")
                .build()
                .create(ShopApi::class.java)
        }
    }

}