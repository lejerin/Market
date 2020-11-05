package com.example.shopping.data.model

data class ProductResponse(
    val count: Int,
    val next: Int,
    val previous: Int,
    val results: List<Product>
)