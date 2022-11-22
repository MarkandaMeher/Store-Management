package com.example.mystore.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Quantity")
data class Quantity (
        @PrimaryKey(autoGenerate = true)
        val id : Int,
        val name: String,
        val quan_in_out : Long,
        val in_out : String,
        val total_quan : Long
        )