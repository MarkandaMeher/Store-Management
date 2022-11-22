package com.example.mystore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.room.Room
import com.example.mystore.db.Store
import com.example.mystore.db.Users
import com.example.mystore.db.UsersDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterStore : AppCompatActivity() {

    lateinit var database: UsersDatabase
    private var inputValidation =  InputValidation()
    lateinit var login1 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_store)

        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            startActivity(parentActivityIntent )
        }
        val title = findViewById<TextView>(R.id.toolbarname)
        title.text="CREATE STORE"

        val email = findViewById<EditText>(R.id.email)
        val store_name = findViewById<EditText>(R.id.store_name)
        val password = findViewById<EditText>(R.id.password)
        val cnfPassword = findViewById<EditText>(R.id.cnf_password)
        val signup = findViewById<Button>(R.id.signup)
        login1 = findViewById(R.id.login)

        database = Room.databaseBuilder(this,
            UsersDatabase::class.java,
            "UsersDB").build()

        signup.setOnClickListener{
            postDataToUsersDB(email,store_name,password,cnfPassword)
        }

        login1.setOnClickListener {
            val i = Intent(this,LoginActivity::class.java)
            startActivity(i)
        }

    }


    private fun postDataToUsersDB(email:EditText,store_name:EditText,password : EditText,cnfPassword : EditText) {
        if (!inputValidation.isInputEditTextFilled(email)) {
            Toast.makeText(this,"Email should not be Empty!", Toast.LENGTH_LONG).show()
            return
        }
        if (!inputValidation.isInputEditTextFilled(store_name)) {
            Toast.makeText(this,"Store Name should not be Empty!", Toast.LENGTH_LONG).show()
            return
        }
        if (!inputValidation.isInputEditTextFilled(password)) {
            Toast.makeText(this,"Password should not be Empty!", Toast.LENGTH_LONG).show()
            return
        }
        if (!inputValidation.isInputEditTextFilled(cnfPassword)) {
            Toast.makeText(this,"Cng Password should not be Empty!", Toast.LENGTH_LONG).show()
            return
        }
        if (!inputValidation.isInputEditTextEmail(email)) {
            Toast.makeText(this,"Email is not valid!", Toast.LENGTH_LONG).show()
            return
        }

        if (!inputValidation.isInputEditTextMatches(password ,cnfPassword)) {
            Toast.makeText(this,"Password and Cnf Password did not match", Toast.LENGTH_LONG ).show()
            return
        }
        var kk = 0
        GlobalScope.launch(Dispatchers.IO) {
            kk = database.usersDAO().checkUser(email.text.toString().trim())
        }

        if (kk<1) {

            GlobalScope.launch(Dispatchers.IO) {
                database.usersDAO().insertStore(Store(0,email.text.toString().trim(), store_name.text.toString().trim(),password.text.toString().trim()))
            }
            Toast.makeText(this,"Store Added Successfully", Toast.LENGTH_LONG).show()
            emptyInputEditText(email,store_name,password,cnfPassword)

            login1.visibility = View.VISIBLE

        }
        else {
            Toast.makeText(this,"Store Already Exists with same Email", Toast.LENGTH_LONG).show()
        }
    }
    private fun emptyInputEditText(email: EditText,phone: EditText,password: EditText,cnfPassword: EditText) {
        email!!.text = null
        phone!!.text = null
        password!!.text = null
        cnfPassword!!.text = null
    }
}