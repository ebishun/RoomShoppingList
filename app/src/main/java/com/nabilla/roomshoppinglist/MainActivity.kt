package com.nabilla.roomshoppinglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nabilla.roomshoppinglist.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var lstShoppingItem:ArrayList<ShoppingItem>

    private val newShoppingItemRequestCode = 1
    private val shoppingItemViewModel: ShoppingItemViewModel by viewModels {
        ShoppingItemViewModelFactory((application as ShoppingApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main)

        val adapter = ShoppingAdapter()
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        lstShoppingItem = ArrayList<ShoppingItem>()
        binding.fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewShoppingItemActivity::class.java)
            startActivityForResult(intent, newShoppingItemRequestCode)
        }

        shoppingItemViewModel.allShoppingItems.observe(this) {items ->
            lstShoppingItem = items as ArrayList<ShoppingItem>
            lstShoppingItem.sort()
            items.let { adapter.submitList(it) }
            displayTotal()
        }

        adapter.setOnItemClickListener(object : ShoppingAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int, whichClicked:Int) {
                when(whichClicked){
                    0 ->  dialogEditItem(position)
                    1 -> {
                        val myShoppingItem = lstShoppingItem[position]
                        if (myShoppingItem.itemStatus == 0){
                            myShoppingItem.itemStatus = 1
                        }else{
                            myShoppingItem.itemStatus = 0
                        }
                        shoppingItemViewModel.updateShoppingItem(myShoppingItem)
                        //displayTotal()
                    }
                }

            }
        })


    }

    private fun displayTotal(){
        var myTotalSum = 0.0
        for (i in lstShoppingItem){
            if (i.itemStatus == 1){
                myTotalSum += (i.itemPrice * i.itemQuantity)
            }
        }
        if (myTotalSum>0){
            binding.layoutTotal.visibility = View.VISIBLE
            val formattedPrice = String.format("%.2f", myTotalSum)
            binding.txtTotal.text = "RM $formattedPrice"
        }else{
            binding.layoutTotal.visibility = View.GONE
        }
    }

    private fun dialogEditItem(position:Int){
        lateinit var dialogAlert: AlertDialog
        val myShoppingItem = lstShoppingItem[position]
        val builder = MaterialAlertDialogBuilder(binding.root.context)
        val view = layoutInflater.inflate(R.layout.dialog_edit_checklist, null)
        builder.setView(view)
        builder.setCancelable(false)

        val edtItemName = view.findViewById<EditText>(R.id.edt_item_name)
        val edtItemQuantity = view.findViewById<EditText>(R.id.edt_item_quantity)
        val edtItemPrice = view.findViewById<EditText>(R.id.edt_item_price)

        val btnNo = view.findViewById<MaterialButton>(R.id.btn_no)
        val btnYes = view.findViewById<MaterialButton>(R.id.btn_yes)

        edtItemName.setText(myShoppingItem.itemName)
        edtItemQuantity.setText(myShoppingItem.itemQuantity.toString())
        edtItemPrice.setText(myShoppingItem.itemPrice.toString())


        btnYes.setOnClickListener {
            if (edtItemQuantity.text.toString().toInt()> 0 && edtItemPrice.text.toString().toDouble()>0){
                myShoppingItem.itemName = edtItemName.text.toString()
                myShoppingItem.itemPrice = edtItemPrice.text.toString().toDouble()
                myShoppingItem.itemQuantity = edtItemQuantity.text.toString().toInt()
                shoppingItemViewModel.updateShoppingItem(myShoppingItem)
            }
            dialogAlert.dismiss()
        }

        btnNo.setOnClickListener {
            dialogAlert.dismiss()
        }

        dialogAlert = builder.show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newShoppingItemRequestCode && resultCode == AppCompatActivity.RESULT_OK) {
            val bundle = intentData!!.extras
            bundle?.let {
                val itemName = it.getString("itemName")
                val itemPrice = it.getString("itemPrice")

                val shoppingItem = ShoppingItem(lstShoppingItem.size,itemName!!,1,itemPrice!!.toDouble(),0)
                shoppingItemViewModel.insertShoppingItem(shoppingItem)
            }
        } else {
            Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_LONG).show()
        }
    }

}