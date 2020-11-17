package com.example.shopping.data.model

import java.io.Serializable

data class Product(
    val id: Int,
    val product_count: Int,
    val product_detail: String,
    val product_image: String,
    val product_major_category: String,
    val product_mark: String,
    val product_merchandiser: String,
    val product_minor_category: String,
    val product_name: String,
    val product_price: Int,
    val product_stock: Int
): Serializable
