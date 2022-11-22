package com.example.mystore.ownerPage

import DashboardAdapter
import android.app.Dialog
import android.os.Bundle
import android.provider.Settings.Global
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.view.get
import androidx.room.Room
import com.example.mystore.InputValidation
import com.example.mystore.R
import com.example.mystore.db.Quantity
import com.example.mystore.db.UsersDatabase
import com.getbase.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DashboardActivity : AppCompatActivity() {

    private var inputValidation =  InputValidation()
    lateinit var database: UsersDatabase
    lateinit var listView: ListView
    lateinit var total_in : TextView
    lateinit var total_in_hand : TextView
    lateinit var total_out : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            startActivity(parentActivityIntent )
        }
        val title = findViewById<TextView>(R.id.toolbarname)
        title.text="Dashboard"

        val product_in = findViewById<FloatingActionButton>(R.id.product_in)
        val product_out = findViewById<FloatingActionButton>(R.id.product_out)

        database = Room.databaseBuilder(applicationContext,
            UsersDatabase::class.java,
            "UsersDB").build()

        listView = findViewById(R.id.listview1)

        val adapter = DashboardAdapter(this,R.layout.list_item, mutableListOf())
        listView.adapter=adapter

        database.usersDAO().getQuantity().observe(this){
            adapter.clear()
            adapter.addAll(it)
        }



        product_in.setOnClickListener {

            val dialog = Dialog(this)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.in_out_dialog)
            val spinner = dialog.findViewById(R.id.spinner) as Spinner
            lateinit var p_name : String
            val current_stock = dialog.findViewById(R.id.editText2) as TextView
            val quantity = dialog.findViewById(R.id.editText3) as EditText
            val in_btn = dialog.findViewById(R.id.in_btn) as Button
            val out_btn = dialog.findViewById(R.id.out_btn) as Button
            val Cancel = dialog.findViewById(R.id.cancel) as Button
            val Add = dialog.findViewById(R.id.add) as Button
            val Close = dialog.findViewById(R.id.close) as ImageView

            var item_spinner = mutableListOf<String>()
            val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, item_spinner)
            spinner.adapter = adapter2

             database.usersDAO().getProductName().observe(this){

                 adapter2.clear()
                     adapter2.addAll(it)
             }
            var i : Int = 0



            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                  spinner.setSelection(position)
                    p_name = item_spinner[position]
                    println(p_name)
                    GlobalScope.launch(Dispatchers.IO) {
                        val items = database.usersDAO().getQuan(p_name)
                        if(items.isEmpty()) {
                            database.usersDAO()
                                .insertQuantity(Quantity(0, p_name, 0, if (i == 0) "IN" else "OUT", 0))

                            withContext(Dispatchers.Main) {
                                current_stock.text = "0"
                            }
                        }
                        else
                            withContext(Dispatchers.Main) {
                                current_stock.text = items.last().total_quan.toString()
                            }
                    }


                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }

//            p_name = "Pen"

//            val pos  = spinner.selectedItemPosition
//            p_name = item_spinner[pos]

            in_btn.setBackgroundColor(resources.getColor(R.color.back))
            out_btn.setBackgroundColor(resources.getColor(R.color.white));

            val inOut : String

            in_btn.setOnClickListener {
                i = 0
                out_btn.setBackgroundColor(resources.getColor(R.color.white))
                in_btn.setBackgroundColor(resources.getColor(R.color.back))
                in_btn.setTextColor(resources.getColor(R.color.black))
            }
            out_btn.setOnClickListener {
                i = 1
                in_btn.setBackgroundColor(resources.getColor(R.color.white));
                out_btn.setBackgroundColor(resources.getColor(R.color.back))
                out_btn.setTextColor(resources.getColor(R.color.black))
            }



            Add.setOnClickListener {
                if(quantity.text.toString().isEmpty()) {
                    Toast.makeText(this,"Quantity Should not be Empty!",Toast.LENGTH_LONG).show()
                }
                else {
                    val current_stock1 = current_stock.text.toString().toLong()
                    val in_out_ = if (i == 0) "IN" else "OUT"
                    if(in_out_=="OUT" && quantity.text.toString().toLong()>current_stock1){
                        Toast.makeText(this,"You don't have that much stock to sell!",Toast.LENGTH_LONG).show()
                    }
                    else{
                        val updated_quan = if(in_out_.equals("IN")) current_stock1+quantity.text.toString().toLong() else current_stock1-quantity.text.toString().toLong()
                        GlobalScope.launch(Dispatchers.IO) {
                            database.usersDAO()
                                .insertQuantity(Quantity(0, p_name, quantity.text.toString().toLong(), if (i == 0) "IN" else "OUT", updated_quan))
                        }
                    }
                }
                database.usersDAO().getQuantity().observe(this){
                    adapter.clear()
                    adapter.addAll(it)
                }
                dialog.dismiss()
            }
            Cancel.setOnClickListener { dialog.dismiss() }
            Close.setOnClickListener { dialog.dismiss() }
            dialog.show()

        }


        product_out.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.in_out_dialog)
            val spinner = dialog.findViewById(R.id.spinner) as Spinner
            var p_name : String
            val current_stock = dialog.findViewById(R.id.editText2) as TextView
            val quantity = dialog.findViewById(R.id.editText3) as EditText
            val in_btn = dialog.findViewById(R.id.in_btn) as Button
            val out_btn = dialog.findViewById(R.id.out_btn) as Button
            val Cancel = dialog.findViewById(R.id.cancel) as Button
            val Add = dialog.findViewById(R.id.add) as Button
            val Close = dialog.findViewById(R.id.close) as ImageView

            var item_spinner = mutableListOf<String>()

            database.usersDAO().getProductName().observe(this){
                item_spinner.addAll(it)
            }


            val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, item_spinner)
            spinner.adapter = adapter2

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    spinner.setSelection(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }

            p_name = "Pen"

//            val pos  = spinner.selectedItemPosition
//            p_name = item_spinner[pos]

            in_btn.setBackgroundColor(resources.getColor(R.color.white))
            out_btn.setBackgroundColor(resources.getColor(R.color.back));

            val inOut : String
            var i : Int = 1

            in_btn.setOnClickListener {
                i = 0
                out_btn.setBackgroundColor(resources.getColor(R.color.white))
                in_btn.setBackgroundColor(resources.getColor(R.color.back))
                in_btn.setTextColor(resources.getColor(R.color.black))
            }
            out_btn.setOnClickListener {
                i = 1
                in_btn.setBackgroundColor(resources.getColor(R.color.white));
                out_btn.setBackgroundColor(resources.getColor(R.color.back))
                out_btn.setTextColor(resources.getColor(R.color.black))
            }

//            database.usersDAO().getQuan(p_name.toString()).observe(this){
//                if(it.isEmpty()) {
//                    GlobalScope.launch(Dispatchers.IO) {
//                        database.usersDAO()
//                            .insertQuantity(Quantity(0, p_name, 0, if (i == 0) "IN" else "OUT", 0))
//                    }
//                    current_stock.text = "0"
//                }
//                else
//                    current_stock.text=it[it.lastIndex].total_quan.toString()
//            }

            Add.setOnClickListener {
                if(quantity.text.toString().isEmpty()) {
                    Toast.makeText(this,"Quantity Should not be Empty!",Toast.LENGTH_LONG).show()
                }
                else {
                    val current_stock1 = current_stock.text.toString().toLong()
                    val in_out_ = if (i == 0) "IN" else "OUT"
                    if(in_out_=="OUT" && quantity.text.toString().toLong()>current_stock1){
                        Toast.makeText(this,"You don't have that much stock to sell!",Toast.LENGTH_LONG).show()
                    }
                    else{
                        val updated_quan = if(in_out_.equals("IN")) current_stock1+quantity.text.toString().toLong() else current_stock1-quantity.text.toString().toLong()
                        GlobalScope.launch(Dispatchers.IO) {
                            database.usersDAO()
                                .insertQuantity(Quantity(0, p_name, quantity.text.toString().toLong(), if (i == 0) "IN" else "OUT", updated_quan))
                        }
                    }
                }
                database.usersDAO().getQuantity().observe(this){
                    adapter.clear()
                    adapter.addAll(it)
                }
                dialog.dismiss()
            }
            Cancel.setOnClickListener { dialog.dismiss() }
            Close.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }

    }
}