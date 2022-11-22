package com.example.mystore.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class Users (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val email : String,
    val phone : Long,
    val password : String
        )