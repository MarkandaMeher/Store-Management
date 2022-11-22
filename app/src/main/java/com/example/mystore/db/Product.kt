package com.example.mystore.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Product")
data class Product (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val product_name : String,
    val product_type: String,
    val mrp : Long
        )