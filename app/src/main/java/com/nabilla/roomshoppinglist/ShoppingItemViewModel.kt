package com.nabilla.roomshoppinglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ShoppingItemViewModel(private val repository:ShoppingItemRepository):ViewModel() {

    val allShoppingItems:LiveData<List<ShoppingItem>> = repository.allItems.asLiveData()

    fun insertShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }

    fun updateShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.updateShoppingItem(shoppingItem)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }

}

class ShoppingItemViewModelFactory(private val repository: ShoppingItemRepository)
    : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShoppingItemViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}