package com.example.shopping.data.model

data class ProductResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Product>
)