package com.example.mystore.ownerPage

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.room.Room
import com.example.mystore.R
import com.example.mystore.db.Limit
import com.example.mystore.db.Product
import com.example.mystore.db.UsersDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SettingActivity : AppCompatActivity() {
    lateinit var database: UsersDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val back = findViewById<ImageView>(R.id.back)
        val addUser = findViewById<RelativeLayout>(R.id.addUser)
        val setLimit = findViewById<RelativeLayout>(R.id.setlimit)
        val changPass = findViewById<RelativeLayout>(R.id.changePass)

        database = Room.databaseBuilder(applicationContext,
            UsersDatabase::class.java,
            "UsersDB").build()

        back.setOnClickListener {
            startActivity(parentActivityIntent )
        }
        val title = findViewById<TextView>(R.id.toolbarname)
        title.text="Settings"

        addUser.setOnClickListener {
            val intent = Intent(this, SignupTabFrag::class.java)
            startActivity(intent)
        }

        changPass.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.changpass_dialog)
            val current_pass = dialog.findViewById(R.id.editText) as EditText
            val new_pass = dialog.findViewById(R.id.editText2) as EditText
            val Cancel = dialog.findViewById(R.id.cancel) as Button
            val save = dialog.findViewById(R.id.add) as Button
            val Close = dialog.findViewById(R.id.close) as ImageView

            save.setOnClickListener {

                if(!check(current_pass,new_pass)) dialog.dismiss()

                else {

                    GlobalScope.launch(Dispatchers.IO) {
                        database.usersDAO().updateStore(current_pass.text.toString(),new_pass.text.toString())
                    }

                }
                dialog.dismiss()

            }
            Cancel.setOnClickListener { dialog.dismiss() }
            Close.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }

        setLimit.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.set_limit)
            val new_limit = dialog.findViewById(R.id.editText) as EditText
            val Cancel = dialog.findViewById(R.id.cancel) as Button
            val save = dialog.findViewById(R.id.add) as Button
            val Close = dialog.findViewById(R.id.close) as ImageView

            save.setOnClickListener {

                if(new_limit.text.isEmpty()) Toast.makeText(this,"New limit should not be empty",Toast.LENGTH_LONG).show()

                else {

                    database.usersDAO().getLimit().observe(this) {
                        if (it.isEmpty()) {
                            GlobalScope.launch(Dispatchers.IO) {
                                database.usersDAO()
                                    .insertLimit(Limit(1, new_limit.text.toString().toLong()))
                            }
                        } else {
                            GlobalScope.launch(Dispatchers.IO) {
                                database.usersDAO()
                                    .updateLimit(1,new_limit.text.toString().toLong())
                            }
                        }
                    }
                    Toast.makeText(this,"Limit Set Successfully!!",Toast.LENGTH_LONG).show()
                }

                dialog.dismiss()

            }
            Cancel.setOnClickListener { dialog.dismiss() }
            Close.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }

        }

    private fun check(etName: EditText, etType:EditText) : Boolean{

        if(etName.text.isEmpty()){
            Toast.makeText(this,"Password should not be Empty!",Toast.LENGTH_LONG).show()
            return false
        }
        if(etType.text.isEmpty()){
            Toast.makeText(this,"New password should not be Empty!",Toast.LENGTH_LONG).show()
            return false
        }

        return true

    }

    }
