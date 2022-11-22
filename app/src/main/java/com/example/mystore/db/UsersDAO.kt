package com.example.mystore.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UsersDAO {

    @Insert
    suspend fun insertUser(user:Users)

    @Insert
    suspend fun insertProduct(product: Product)

    @Insert
    suspend fun insertStore(store: Store)

    @Query("UPDATE Store SET password=:new_password WHERE password=:password")
    suspend fun updateStore(password: String,new_password : String)

    @Insert
    suspend fun insertProductDetails(ProductDetails : ProductsDetails)

    @Insert
    suspend fun insertQuantity(quantity: Quantity)

    @Query("SELECT * FROM Quantity")
    fun getQuantity() : LiveData<List<Quantity>>

    @Query("SELECT * FROM Quantity WHERE name=:name")
    fun getQuan(name: String) : List<Quantity>

    @Query("SELECT * FROM store")
    fun getAllStore() : LiveData<List<Store>>

    @Insert
    suspend fun insertLimit(limit: Limit)

    @Query("UPDATE `Limit` SET `limit`=:n WHERE id=:id")
    suspend fun updateLimit(id: Int,n : Long)

    @Query("SELECT * FROM `limit`")
    fun getLimit(): LiveData<List<Limit>>

    @Query("SELECT * FROM Quantity WHERE total_quan<=:limit")
    fun getLimitProduct(limit: Long) : LiveData<List<Quantity>>

    @Query("UPDATE Quantity SET quan_in_out=:quan_in_out, in_out=:in_out,total_quan=:total_q WHERE name=:name")
    fun updateQuan(name: String,quan_in_out : Long,in_out : String,total_q : Long)

    @Query("UPDATE ProductsDetails SET total_products = :total_p, total_stock_in_hand = :total_s, total_stock_price = :total_m WHERE id=:id")
     fun updateProductDetails(id : Int,total_p:Long,total_s:Long,total_m:Long)

     @Query("SELECT product_name FROM Product")
     fun getProductName() : LiveData<List<String>>

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("SELECT * FROM ProductsDetails WHERE id=:id")
    fun getProductDetails(id:Int) : LiveData<List<ProductsDetails>>

    @Update
    suspend fun updateUser(user: Users)

    @Delete
    suspend fun deleteUser(user: Users)

    @Query("SELECT * FROM Users")
    fun getUsers(): LiveData<List<Users>>

    @Query("SELECT * FROM Product")
    fun getProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM STORE WHERE email=:email AND password=:password")
    fun getStores(email: String,password: String) : LiveData<List<Store>>

    @Query("SELECT COUNT(id) as total_records FROM Product")
    fun getCountProduct() : Int

    @Query("SELECT * FROM Users WHERE phone=:name")
    fun getPassword(name: String): LiveData<List<Users>>

    @Query("SELECT COUNT() FROM Users WHERE email = :email")
    fun checkUser(email: String): Int

    @Query("SELECT * FROM Users WHERE email LIKE :name AND password LIKE:password")
    fun readAllData(name: String, password: String): LiveData<List<Users>>

    @Query("SELECT password FROM Users WHERE email=:email")
    fun readPassword(email: String): String

}