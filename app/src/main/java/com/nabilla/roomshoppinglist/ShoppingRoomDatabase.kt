package com.nabilla.roomshoppinglist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ShoppingItem::class],
    version = 1,
    exportSchema = false
)
public abstract class ShoppingRoomDatabase:RoomDatabase() {

    abstract fun shoppingItemDao():ShoppingItemDao

    companion object{
        @Volatile
        private var INSTANCE: ShoppingRoomDatabase? = null

        fun getDatabase(context: Context): ShoppingRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val  instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShoppingRoomDatabase::class.java,
                    "shopping_db"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }

}