package com.datojo.socialpet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewModelScope
import com.datojo.socialpet.Model.Items
import com.datojo.socialpet.Model.Pet
import com.datojo.socialpet.View.Navigation
import com.datojo.socialpet.ViewModel.Inventory
import com.datojo.socialpet.ViewModel.PetStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate


class MainActivity : ComponentActivity() {
    private val stats: PetStatus by viewModels()
    private val inventory: Inventory by viewModels()
    private val cat =
        Pet("Test", "Test", 0, .7f, .5f, .5f, Date())
    private val items =
        Items(1, 1, 1)

    private val contacts = listOf("Jonas Lindek", "Tom Küper", "Daniel Sonnenberg")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            //set stats from storage
            stats.setStats(cat.readFromInternalStorage("petStats", applicationContext))
            inventory.setItems(items.readFromInternalStorage("inventory", applicationContext))
            Navigation(stats, inventory, contacts)
        }

        //calc stat change after time away and update livedata in background
        stats.viewModelScope.launch(Dispatchers.Default) {
            stats.calcStats(true)
            Timer().scheduleAtFixedRate(1000, 1000) {
                stats.calcStats()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        stats.saveStats(cat).saveToInternalStorage("petStats", applicationContext)
        inventory.saveItems(items).saveToInternalStorage("inventory", applicationContext)
    }
}

@Composable
fun Background(id: Int, description: String, modifier: Modifier) {
    Image(
        painter = painterResource(id = id),
        contentDescription = description,
        modifier = modifier
    )
}