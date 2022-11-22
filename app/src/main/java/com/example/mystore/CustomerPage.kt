package com.example.mystore

import ProductAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.room.Room
import com.example.mystore.db.UsersDatabase

class CustomerPage : AppCompatActivity() {
    lateinit var database: UsersDatabase
    lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_page)

        val back = findViewById<ImageView>(R.id.back)
        val title = findViewById<TextView>(R.id.toolbarname)

        database = Room.databaseBuilder(applicationContext,
            UsersDatabase::class.java,
            "UsersDB").build()

        database.usersDAO().getAllStore().observe(this){
            title.text=it[0].store_name
        }

        back.setOnClickListener {
            startActivity(parentActivityIntent )
        }

        listView = findViewById<ListView>(R.id.listview1)

        val adapter = ProductAdapter(this,R.layout.list_item_2, mutableListOf())
        listView.adapter=adapter

        database.usersDAO().getProducts().observe(this){
            adapter.clear()
            adapter.addAll(it)
        }





    }
}