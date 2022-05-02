package com.example.unittestinginandroid.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    //Reason this is not a suspend function is because LiveData is asynchronous by default
    //Whenever you return a LiveData with Room, you don't want to make that `suspend` function
    @Query(value = "SELECT * FROM shopping_items")
    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    @Query(value = "SELECT SUM(price * quantity) FROM shopping_items")
    fun observeTotalPrice(): LiveData<Float>
}