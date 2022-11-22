package com.example.mystore

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.mystore.db.UsersDatabase
import com.example.mystore.ownerPage.OwnerMainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OwnerTabFrag : Fragment() {

    lateinit var database: UsersDatabase
    private var inputValidation =  InputValidation()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.owner_tab_layout,container,false)

        database = Room.databaseBuilder(requireActivity(),
            UsersDatabase::class.java,
            "UsersDB").build()

        val email = view.findViewById<EditText>(R.id.email1)
        val password = view.findViewById<EditText>(R.id.password1)
        val signup = view.findViewById<Button>(R.id.signup1)

        signup.setOnClickListener {
                database.usersDAO().getStores(email.text.toString(),password.text.toString()).observe(requireActivity()){
                    if(it.isEmpty()){
                        Toast.makeText(requireActivity(),"Asses Denied",Toast.LENGTH_LONG).show()
                    }
                    else{
                        if (email.text.isNotEmpty() && password.text.isNotEmpty()) {
                            if(email.text.toString()==it[0].email && password.text.toString()==it[0].password){

                                val intent = Intent(requireActivity(), OwnerMainActivity::class.java)
                                startActivity(intent)
                            }
                            else{
                                Toast.makeText(requireActivity(),"Password is Incorrect!", Toast.LENGTH_LONG).show()
                            }
                        }
                        else{
                            Toast.makeText(requireActivity(),"password Should not be Empty!", Toast.LENGTH_LONG).show()
                        }
                    }
                }

        }

        emptyInputEditText(email,password)

        return view

    }

    private fun emptyInputEditText(email: EditText,password: EditText) {
        email!!.text = null
        password!!.text = null
    }

}