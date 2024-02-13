package com.nabilla.roomshoppinglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nabilla.roomshoppinglist.databinding.LayoutShoppingItemBinding
import java.text.DecimalFormat

/*
class ShoppingAdapter: ListAdapter<ShoppingItem, ShoppingAdapter.ShoppingItemViewHolder>(ShoppingItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingAdapter.ShoppingItemViewHolder {
        return ShoppingItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.itemName, current.itemPrice, current.itemQuantity)
    }

    class ShoppingItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val txtItemName: TextView = itemView.findViewById(R.id.txt_shopping_name)
        private val txtItemPrice: TextView = itemView.findViewById(R.id.txt_shopping_price)

        fun bind(itemName: String?, itemPrice:Double?, itemQuantity:Int?) {
            txtItemName.text = itemName
            txtItemPrice.text = "RM ${itemPrice.toString()}"
        }

        companion object {
            fun create(parent: ViewGroup): ShoppingItemViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_shopping_item, parent, false)
                return ShoppingItemViewHolder(view)
            }
        }

    }

    class ShoppingItemComparator: DiffUtil.ItemCallback<ShoppingItem>(){
        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.itemName == newItem.itemName
        }

    }
}
*/

class ShoppingAdapter : ListAdapter<ShoppingItem, ShoppingAdapter.ShoppingAdapterViewHolder>(ShoppingItemComparator()) {
    private var mListener: OnItemClickListener? = null
    inner class ShoppingAdapterViewHolder(val binding: LayoutShoppingItemBinding): RecyclerView.ViewHolder(binding.root){

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingAdapterViewHolder {
        return ShoppingAdapterViewHolder(
            LayoutShoppingItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: ShoppingAdapterViewHolder, position: Int) {
        holder.binding.apply {
            val shoppingItem = getItem(position)
            val formattedPrice = String.format("%.2f", shoppingItem.itemPrice)
            txtShoppingName.text = shoppingItem.itemName
            txtShoppingPrice.text = "RM $formattedPrice"
            txtShoppingQuantity.text = "Quantity: ${shoppingItem.itemQuantity}"

            cardShoppingItem.setOnClickListener {
                mListener!!.onItemClick(it, position,0)
            }

            checkItem.isChecked = shoppingItem.itemStatus == 1


            checkItem.setOnClickListener {
                mListener!!.onItemClick(it, position,1)
            }

        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, whichClicked:Int)
    }

    //override fun getItemCount() = listShoppingItem.size
    override fun getItemViewType(position: Int) = position
    override fun getItemId(position: Int) = position.toLong()

    class ShoppingItemComparator: DiffUtil.ItemCallback<ShoppingItem>(){
        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.itemName == newItem.itemName
        }

    }

}