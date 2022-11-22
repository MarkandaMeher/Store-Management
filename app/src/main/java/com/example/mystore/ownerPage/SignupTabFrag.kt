package com.example.mystore.ownerPage

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.mystore.InputValidation
import com.example.mystore.R
import com.example.mystore.db.Users
import com.example.mystore.db.UsersDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignupTabFrag : AppCompatActivity() {

    lateinit var database: UsersDatabase
    private var inputValidation =  InputValidation()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_tab_frag)


        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            startActivity(parentActivityIntent )
        }
        val title = findViewById<TextView>(R.id.toolbarname)
        title.text="Add User"
        

        val email = findViewById<EditText>(R.id.email)
        val phone = findViewById<EditText>(R.id.phone)
        val password = findViewById<EditText>(R.id.password)
        val cnfPassword = findViewById<EditText>(R.id.cnf_password)
        val signup = findViewById<Button>(R.id.signup)

        database = Room.databaseBuilder(this,
            UsersDatabase::class.java,
            "UsersDB").build()

        signup.setOnClickListener{
            postDataToUsersDB(email,phone,password,cnfPassword)
        }

    }

    private fun postDataToUsersDB(email:EditText,phone:EditText,password : EditText,cnfPassword : EditText) {
        if (!inputValidation.isInputEditTextFilled(email)) {
            Toast.makeText(this,"Email should not be Empty!",Toast.LENGTH_LONG).show()
            return
        }
        if (!inputValidation.isInputEditTextFilled(phone)) {
            Toast.makeText(this,"Phone should not be Empty!",Toast.LENGTH_LONG).show()
            return
        }
        if (!inputValidation.isInputEditTextFilled(password)) {
            Toast.makeText(this,"Password should not be Empty!",Toast.LENGTH_LONG).show()
            return
        }
        if (!inputValidation.isInputEditTextFilled(cnfPassword)) {
            Toast.makeText(this,"Cng Password should not be Empty!",Toast.LENGTH_LONG).show()
            return
        }
        if (!inputValidation.isInputEditTextEmail(email)) {
            Toast.makeText(this,"Email is not valid!",Toast.LENGTH_LONG).show()
            return
        }
        if(!inputValidation.isInputEditTextNumber(phone)){
            Toast.makeText(this,"Phone number Should be Numbers Only!",Toast.LENGTH_LONG).show()
            return
        }

        if (!inputValidation.isInputEditTextMatches(password ,cnfPassword)) {
            Toast.makeText(this,"Password and Cnf Password did not match",Toast.LENGTH_LONG ).show()
            return
        }
        var kk = 0
        GlobalScope.launch(Dispatchers.IO) {
             kk = database.usersDAO().checkUser(email.text.toString().trim())
        }

        if (kk<1) {

            GlobalScope.launch(Dispatchers.IO) {
                database.usersDAO().insertUser(Users(0,email.text.toString().trim(), phone.text.toString().toLong(),password.text.toString().trim()))
            }
            Toast.makeText(this,"User Added Successfully",Toast.LENGTH_LONG).show()
            emptyInputEditText(email,phone,password,cnfPassword)
        }
        else {
            Toast.makeText(this,"User Already Exists with same Email",Toast.LENGTH_LONG).show()
        }
    }
    private fun emptyInputEditText(email: EditText,phone: EditText,password: EditText,cnfPassword: EditText) {
        email!!.text = null
        phone!!.text = null
        password!!.text = null
        cnfPassword!!.text = null
    }

}