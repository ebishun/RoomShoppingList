package com.nabilla.roomshoppinglist

import android.app.Application

class ShoppingApplication: Application() {

    val database by lazy { ShoppingRoomDatabase.getDatabase(this)}
    val repository by lazy { ShoppingItemRepository(database.shoppingItemDao()) }

}