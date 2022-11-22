package com.example.mystore

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.viewpager.widget.ViewPager
import com.example.mystore.db.UsersDatabase
import com.getbase.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class LoginActivity : AppCompatActivity() {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    lateinit var about : TextView
    lateinit var owner : TextView
    lateinit var call : FloatingActionButton
    lateinit var email : FloatingActionButton
    lateinit var message : FloatingActionButton

    lateinit var database: UsersDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.view_pager)
        about = findViewById(R.id.about)
        call = findViewById(R.id.call)
        email = findViewById(R.id.email)
        message = findViewById(R.id.message)

        database = Room.databaseBuilder(applicationContext,
            UsersDatabase::class.java,
            "UsersDB").build()


        tabLayout.addTab(tabLayout.newTab().setText("Login"))
        tabLayout.addTab(tabLayout.newTab().setText("Owner"))
        tabLayout.tabGravity=TabLayout.GRAVITY_FILL

        val loginAdapter = LoginAdapter(supportFragmentManager,this,tabLayout.tabCount)
        viewPager.adapter=loginAdapter

        tabLayout.setupWithViewPager(viewPager)

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        call.setOnClickListener {
            val callIntent: Intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:9348922189"))
            startActivity(callIntent)
        }

        email.setOnClickListener {
           val emailIntent =  Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("piyushmeher100@gmail.com"))
            }
            startActivity(emailIntent)
        }

        message.setOnClickListener {
            val sendIntent = Intent(Intent.ACTION_VIEW)
            sendIntent.data = Uri.parse("sms:")
            startActivity(sendIntent)
        }


        }


    }