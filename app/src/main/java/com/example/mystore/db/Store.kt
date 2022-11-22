package com.example.mystore.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Store")
data class Store (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val email : String,
    val store_name : String,
    val password : String,
        )