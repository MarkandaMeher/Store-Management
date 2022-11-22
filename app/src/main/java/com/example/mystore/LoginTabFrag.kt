package com.example.mystore

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.mystore.db.Users
import com.example.mystore.db.UsersDatabase
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginTabFrag : Fragment() {

    lateinit var database: UsersDatabase
    private var inputValidation =  InputValidation()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.login_tab_frag,container,false)

        val email = view.findViewById<EditText>(R.id.email)
        val password = view.findViewById<EditText>(R.id.password)
        val login = view.findViewById<Button>(R.id.login)

        database = Room.databaseBuilder(requireActivity(),
            UsersDatabase::class.java,
            "UsersDB").build()

        login.setOnClickListener{
            verifyFromSQLite(email,password)
        }

        val forgetPass = view.findViewById<TextView>(R.id.forgetPass)
        forgetPass.setOnClickListener {
            val builder = AlertDialog.Builder(requireActivity())
            builder.setTitle("Forget Password")
            var linearLayout = LinearLayout(activity)
            linearLayout.orientation = LinearLayout.VERTICAL

            val idView = EditText(activity)
            idView.hint="Enter Email Address Here"
            linearLayout.addView(idView)

            val find = Button(activity)
            find.text = "FIND"
            find.height= 30
            find.width = 30
            linearLayout.addView(find)

            val idView2 = TextView(activity)
            idView2.hint="Password will visible here"
            linearLayout.addView(idView2)

            find.setOnClickListener {
                if (!inputValidation.isInputEditTextFilled(idView)) {
                    Toast.makeText(requireActivity(),"Email should not be Empty!",Toast.LENGTH_LONG).show()
                }
                if(!inputValidation.isInputEditTextEmail(idView)){
                    Toast.makeText(requireActivity(),"Email is not Valid!",Toast.LENGTH_LONG).show()
                }
                else{
                    GlobalScope.launch {
                        val temp =  database.usersDAO().readPassword(idView.text.toString().trim())
                        idView2.text = "Your Password is : "+ temp
                    }

                }
            }

            builder.setView(linearLayout)


            builder.setPositiveButton("Done") { _, _ ->
                Toast.makeText(requireActivity(),"You can now LogIn",Toast.LENGTH_LONG).show()
            }
            builder.setNegativeButton("Cancel") { it, _ -> it.dismiss() }
            builder.create().show()

        }

        return view
    }
    private fun verifyFromSQLite(email:EditText, password:EditText) {
        if (!inputValidation.isInputEditTextFilled(email)) {
            Toast.makeText(requireActivity(),"Email should not be Empty!",Toast.LENGTH_LONG).show()
            return
        }
        if (!inputValidation.isInputEditTextEmail(email)) {
            Toast.makeText(requireActivity(),"Enter a valid Email",Toast.LENGTH_LONG).show()
            return
        }
        if (!inputValidation.isInputEditTextFilled(password)) {
            Toast.makeText(requireActivity(),"Password should not be Empty!",Toast.LENGTH_LONG).show()
            return
        }

        database.usersDAO().readAllData(email.text.toString().trim(), password.text.toString().trim()).observe(requireActivity()) {
            if (it.isNotEmpty()) {

            val i = Intent(requireActivity(),CustomerPage::class.java)
                startActivity(i)

            emptyInputEditText(email,password)
        }
        else {
            Toast.makeText(activity,"Password Or Email is wrong", Toast.LENGTH_LONG).show()
         }
        }

    }
    private fun emptyInputEditText(email: EditText,password: EditText) {
        email!!.text = null
        password!!.text = null
    }

}