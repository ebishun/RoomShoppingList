package com.nabilla.roomshoppinglist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_item_table")
data class ShoppingItem (
    @PrimaryKey(autoGenerate = false) val id: Int = 0,
    @ColumnInfo(name = "item_name")
    var itemName:String,
    @ColumnInfo(name = "item_quantity")
    var itemQuantity:Int,
    @ColumnInfo(name = "item_price")
    var itemPrice:Double,
    @ColumnInfo(name = "item_status")
    var itemStatus:Int

):Comparable<ShoppingItem>{
    override fun compareTo(other: ShoppingItem): Int {
        return this.itemName.compareTo(other.itemName)
    }
}