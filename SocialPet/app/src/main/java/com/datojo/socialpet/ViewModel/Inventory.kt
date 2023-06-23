package com.datojo.socialpet.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.datojo.socialpet.Model.Items

class Inventory: ViewModel() {
    private var _food = mutableStateOf(1)
    private var _water = mutableStateOf(1)
    private var _currency = mutableStateOf(1)

    val food: State<Int> = _food
    val water: State<Int> = _water
    val currency: State<Int> = _currency

    fun setItems(items: Items) {
        _food.value = items.food
        _water.value = items.water
        _currency.value = items.currency
    }

    fun saveItems(items: Items): Items {
        items.food = _food.value
        items.water = _water.value
        items.currency = _currency.value

        return items
    }

    fun isEmptyFood(): Boolean {
        if (_food.value <= 0) return true
        return false
    }

    fun isEmptyWater(): Boolean {
        if (_water.value <= 0) return true
        return false
    }

    fun subFood() {
        _food.value--
        if (_food.value < 0) _food.value = 0
    }

    fun subWater() {
        _water.value--
        if (_water.value < 0) _water.value = 0
    }

    fun addFood() {
        _food.value++
    }

    fun addWater() {
        _water.value++
    }

    fun enoughCurrency(price: Int): Boolean {
        if (_currency.value >= price) return true
        return false
    }

    fun subCurrency(price: Int) {
        _currency.value -= price
    }

    fun addCurrency(money: Int) {
        _currency.value += money
    }
}