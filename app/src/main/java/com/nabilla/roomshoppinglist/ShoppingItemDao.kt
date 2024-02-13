package com.nabilla.roomshoppinglist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface ShoppingItemDao {

    @Query("SELECT * FROM shopping_item_table ORDER BY item_name ASC")
    fun getAllShoppingItems(): Flow<List<ShoppingItem>>

    //@Insert(onConflict = OnConflictStrategy.IGNORE)
    @Upsert
    suspend fun insertItem(shoppingItem: ShoppingItem)

    @Update
    suspend fun updateItem(shoppingItem: ShoppingItem)

    @Delete
    suspend fun deleteItem(shoppingItem: ShoppingItem)

    @Query("DELETE FROM shopping_item_table")
    suspend fun deleteAll()

}