package com.example.mystore.ownerPage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.mystore.R

class OwnerMainActivity : AppCompatActivity() {

    private lateinit var dashboard : RelativeLayout
    lateinit var products : RelativeLayout
    lateinit var lowstock : RelativeLayout
    lateinit var transactions : RelativeLayout
    lateinit var settings : RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_main)

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        dashboard = findViewById(R.id.dashboard)
        products = findViewById(R.id.products)
        lowstock = findViewById(R.id.lowstock)
        settings = findViewById(R.id.settings)

        dashboard.setOnClickListener {
            val das = Intent(this,DashboardActivity::class.java)
            startActivity(das)
        }

        products.setOnClickListener {
            val das = Intent(this,ProductsActivity::class.java)
            startActivity(das)
        }

        lowstock.setOnClickListener {
            val i = Intent(this,LimitActivity::class.java)
            startActivity(i)
        }

        settings.setOnClickListener {
            val set = Intent(this,SettingActivity::class.java)
            startActivity(set)
        }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id:Int = item.itemId
        if(id==R.id.action_settings)
        {
            val settingIntent =  Intent(this,SettingActivity::class.java)
            startActivity(settingIntent)
            return true
        }
        else if(id == R.id.action_email)
        {
            val emailIntent =  Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("piyushmeher100@gmail.com"))
            }
            startActivity(emailIntent)
            return true
        }
        else if (id == R.id.action_info)
        {
            val url = "https://github.com/MarkandaMeher?tab=repositories"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
            return true
        }
        return super.onOptionsItemSelected(item)
    }



}