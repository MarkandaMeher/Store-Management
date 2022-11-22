package com.example.mystore.ownerPage

import LimitAdapter
import ProductAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.room.Room
import com.example.mystore.R
import com.example.mystore.db.Limit
import com.example.mystore.db.UsersDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LimitActivity : AppCompatActivity() {
    lateinit var database: UsersDatabase
    lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_limit)

        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            startActivity(parentActivityIntent )
        }
        val title = findViewById<TextView>(R.id.toolbarname)
        val number = findViewById<TextView>(R.id.number)
        title.text="Limited Product Available"

        database = Room.databaseBuilder(applicationContext,
            UsersDatabase::class.java,
            "UsersDB").build()


        listView = findViewById<ListView>(R.id.listview1)

        val adapter = LimitAdapter(this,R.layout.limit_list_item, mutableListOf())
        listView.adapter=adapter

        database.usersDAO().getLimitProduct(10).observe(this){
            adapter.clear()
            adapter.addAll(it)
        }

        database.usersDAO().getLimit().observe(this){
            if(it.isEmpty()) number.text = "10"
            number.text = it[0].limit.toString()
        }

        database.usersDAO().getLimit().observe(this){ it2 ->
            if (it2.isEmpty()) {
                    GlobalScope.launch(Dispatchers.IO) {
                        database.usersDAO()
                            .insertLimit(Limit(1, 10))
                    }
                    database.usersDAO().getLimitProduct(10).observe(this){
                        adapter.clear()
                        adapter.addAll(it)
                    }

                } else {
                    database.usersDAO().getLimitProduct(10).observe(this){
                        adapter.clear()
                        adapter.addAll(it)
                    }
                    }
                }
            }
        }

