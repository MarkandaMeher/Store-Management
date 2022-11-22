package com.example.mystore.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Users::class,Product::class,ProductsDetails::class,Store::class,Quantity::class,Limit::class], version = 1)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun usersDAO() : UsersDAO
}