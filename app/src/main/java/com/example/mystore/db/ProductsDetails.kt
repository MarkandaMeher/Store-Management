package com.example.mystore.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ProductsDetails")
data class ProductsDetails (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val total_products : Long,
    val total_stock_in_hand: Long,
    val total_stock_price : Long
        )