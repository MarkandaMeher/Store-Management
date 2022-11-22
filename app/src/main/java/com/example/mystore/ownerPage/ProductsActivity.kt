package com.example.mystore.ownerPage

import ProductAdapter
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.core.view.isEmpty
import androidx.room.Room
import com.example.mystore.InputValidation
import com.example.mystore.R
import com.example.mystore.db.Product
import com.example.mystore.db.ProductsDetails
import com.example.mystore.db.UsersDatabase
import com.getbase.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductsActivity : AppCompatActivity() {
    private var inputValidation =  InputValidation()
    lateinit var database: UsersDatabase
    lateinit var listView: ListView
    lateinit var total_products : TextView
    lateinit var total_stock_in_hand : TextView
    lateinit var total_stock_price : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

//        total_products = findViewById(R.id.total_product)
//        total_stock_in_hand = findViewById(R.id.total_stock_in_hand)
//        total_stock_price = findViewById(R.id.total_price)

        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            startActivity(parentActivityIntent )
        }
        val title = findViewById<TextView>(R.id.toolbarname)
        title.text="Products"

        database = Room.databaseBuilder(applicationContext,
            UsersDatabase::class.java,
            "UsersDB").build()


        listView = findViewById<ListView>(R.id.listview1)

        val adapter = ProductAdapter(this,R.layout.product_list_item, mutableListOf())
        listView.adapter=adapter

        database.usersDAO().getProducts().observe(this){
            adapter.clear()
            adapter.addAll(it)
        }

//        if(adapter.count==0){
//            GlobalScope.launch(Dispatchers.IO) {
//                database.usersDAO().insertProductDetails(ProductsDetails(1, 0,0,0))
//            }
//            updateDetails(total_products,total_stock_in_hand,total_stock_price)
//        }
//        else{
//            updateDetails(total_products,total_stock_in_hand,total_stock_price)
//        }

        listView.setOnItemClickListener { parent, _, position,_ ->
            val dialog = Dialog(this)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.edit_dialog)
            var etName = dialog.findViewById(R.id.editText) as EditText
            var etType = dialog.findViewById(R.id.editText2) as EditText
            var rate = dialog.findViewById(R.id.editText3) as EditText
            var delete = dialog.findViewById(R.id.delete) as Button
            var save = dialog.findViewById(R.id.Save) as Button
            var Close = dialog.findViewById(R.id.close) as ImageView

            val view = parent[position]
            etName.setText(view.findViewById<TextView>(R.id.product_name).text.toString())
            etType.setText(view.findViewById<TextView>(R.id.product_type).text.toString())
            rate.setText(view.findViewById<TextView>(R.id.mrp).text.toString())
            val id : Int = view.findViewById<TextView>(R.id.id).text.toString().toInt()
            val p_name : String = view.findViewById<TextView>(R.id.product_name).text.toString()
            val p_type : String = view.findViewById<TextView>(R.id.product_type).text.toString()
            val p_price : Long = view.findViewById<TextView>(R.id.mrp).text.toString().toLong()

            save.setOnClickListener {

                if(!check(etName,etType,rate)) dialog.dismiss()

                else {

                    val Pname = etName.text.toString()
                    val Ptype = etType.text.toString()
                    val buyRate = rate.text.toString().toLong()
                    GlobalScope.launch(Dispatchers.IO) {
                        database.usersDAO().updateProduct(Product(id,Pname,Ptype,buyRate))
                    }
                    database.usersDAO().getProducts().observe(this){
                        adapter.clear()
                        adapter.addAll(it)

                    }
                    dialog.dismiss()

                }
            }

            delete.setOnClickListener {
                GlobalScope.launch(Dispatchers.IO) {
                    database.usersDAO().deleteProduct(Product(id,p_name,p_type,p_price))
                }

//                if(adapter.count!=0){
//                    subPDetails()
//                }


                database.usersDAO().getProducts().observe(this){
                    adapter.clear()
                    adapter.addAll(it)

                }

                dialog.dismiss()
            }
            Close.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }


        val add = findViewById<FloatingActionButton>(R.id.product_add)
        add.setOnClickListener {

            val dialog = Dialog(this)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.add_dialog)
            val etName = dialog.findViewById(R.id.editText) as EditText
            val etType = dialog.findViewById(R.id.editText2) as EditText
            val rate = dialog.findViewById(R.id.editText3) as EditText
            val Cancel = dialog.findViewById(R.id.cancel) as Button
            val Add = dialog.findViewById(R.id.add) as Button
            val Close = dialog.findViewById(R.id.close) as ImageView

            Add.setOnClickListener {

                if(!check(etName,etType,rate)) dialog.dismiss()

                else {

                    val Pname = etName.text.toString()
                    val Ptype = etType.text.toString()
                    val buyRate = rate.text.toString().toLong()
                    GlobalScope.launch(Dispatchers.IO) {
                        database.usersDAO().insertProduct(Product(0, Pname, Ptype, buyRate))
                    }
                    database.usersDAO().getProducts().observe(this){
                        adapter.clear()
                        adapter.addAll(it)
                    }
                }
//                addPDetails()
                dialog.dismiss()

            }
            Cancel.setOnClickListener { dialog.dismiss() }
            Close.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }



        }
    private fun check(etName: EditText, etType:EditText, rate:EditText) : Boolean{

        if (!inputValidation.isInputEditTextFilled(etName)) {
            Toast.makeText(this,"Product Name Should not be Empty!",Toast.LENGTH_LONG).show()
            return false
        }
        if (!inputValidation.isInputEditTextFilled(etType)) {
            Toast.makeText(this,"Product Type should not be Empty!",Toast.LENGTH_LONG).show()
            return false
        }
        if (!inputValidation.isInputEditTextFilled(rate)) {
            Toast.makeText(this,"Buy Rate should not be Empty!",Toast.LENGTH_LONG).show()
            return false
        }

        return true

    }

    private fun updateDetails(total_products : TextView,total_stock_in_hand : TextView,total_price : TextView){

        database.usersDAO().getProductDetails(1).observe(this) {
            total_products.text = it[0].total_products.toString()
            total_stock_in_hand.text = it[0].total_stock_in_hand.toString()
            total_price.text = it[0].total_stock_price.toString()
        }

    }


    private fun addPDetails(){
        database.usersDAO().getProductDetails(1).observe(this) {
          var tp = it[0].total_products
           var th = it[0].total_stock_in_hand
           var tpr = it[0].total_stock_price

            tp+=1

            GlobalScope.launch(Dispatchers.IO) {
                database.usersDAO().updateProductDetails(1, tp,th,tpr)
            }


            updateDetails(total_products,total_stock_in_hand,total_stock_price)


        }

    }


    private fun subPDetails(){
        database.usersDAO().getProductDetails(1).observe(this) {
            var tp = it[0].total_products
            var th = it[0].total_stock_in_hand
            var tpr = it[0].total_stock_price

            tp -= 1

            GlobalScope.launch(Dispatchers.IO) {
                database.usersDAO().updateProductDetails(1, tp,th,tpr)
            }
            updateDetails(total_products,total_stock_in_hand,total_stock_price)


        }

    }



    }
