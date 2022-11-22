package com.example.mystore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.widget.Toast
import androidx.room.Room
import com.example.mystore.db.UsersDatabase
import com.example.mystore.ownerPage.OwnerMainActivity

class MainActivity : AppCompatActivity() {
    lateinit var database: UsersDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        database = Room.databaseBuilder(this,
            UsersDatabase::class.java,
            "UsersDB").build()

        database.usersDAO().getAllStore().observe(this){
            if(it.isEmpty()){
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        val i = Intent(this, FirstPage::class.java)
                        startActivity(i)
                        finish() },2000 )
            }
            else{
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        val i = Intent(this, LoginActivity::class.java)
                        startActivity(i)
                        finish() },2000 )
            }
        }

    }
}