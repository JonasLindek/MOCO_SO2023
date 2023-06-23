package com.datojo.socialpet.View.overlays

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.datojo.socialpet.ViewModel.Inventory

@Composable
fun ShopOverlay(inventory: Inventory) {
    var shopPopUpControl by remember { mutableStateOf(false) }

    if(shopPopUpControl)
        ShopPopUp({ change -> shopPopUpControl = change }, inventory)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
           TextButton(onClick = {shopPopUpControl = true}, name = "Shop")
    }
}