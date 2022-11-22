package com.example.mystore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.room.Room
import com.example.mystore.db.UsersDatabase
import com.example.mystore.ownerPage.OwnerMainActivity

class FirstPage : AppCompatActivity() {
    lateinit var database: UsersDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)



        val login = findViewById<Button>(R.id.appCompatButton)
        val new_store = findViewById<Button>(R.id.new_store)

        login.setOnClickListener {
            val i = Intent(this,LoginActivity::class.java)
            startActivity(i)
        }

        new_store.setOnClickListener {
            val i = Intent(this,RegisterStore::class.java)
            startActivity(i)
        }


    }
}