package com.example.mystore.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Limit")
data class Limit (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val limit: Long
        )