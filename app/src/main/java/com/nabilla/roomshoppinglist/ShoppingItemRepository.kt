package com.nabilla.roomshoppinglist

import android.util.Log
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class ShoppingItemRepository(private val shoppingItemDao: ShoppingItemDao) {

    val allItems:Flow<List<ShoppingItem>> = shoppingItemDao.getAllShoppingItems()

    @WorkerThread
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem){
        shoppingItemDao.insertItem(shoppingItem)
    }

    @WorkerThread
    suspend fun updateShoppingItem(shoppingItem: ShoppingItem){
        shoppingItemDao.updateItem(shoppingItem)
    }

    @WorkerThread
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem){
        shoppingItemDao.deleteItem(shoppingItem)
    }

}