package com.nabilla.roomshoppinglist

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.nabilla.roomshoppinglist.databinding.ActivityNewShoppingItemBinding


class NewShoppingItemActivity : AppCompatActivity() {

    lateinit var binding:ActivityNewShoppingItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_shopping_item)


        binding.buttonSave.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(binding.edtShoppingItem.text)){
                setResult(AppCompatActivity.RESULT_CANCELED, replyIntent)
            }else{
                val myItemName = binding.edtShoppingItem.text.toString()
                val myItemPrice = binding.edtShoppingPrice.text.toString()
                val myBundle = Bundle()
                myBundle.putString("itemName",myItemName)
                myBundle.putString("itemPrice",myItemPrice)
                replyIntent.putExtras(myBundle)

                setResult(AppCompatActivity.RESULT_OK, replyIntent)
            }
            finish()
        }

    }

}