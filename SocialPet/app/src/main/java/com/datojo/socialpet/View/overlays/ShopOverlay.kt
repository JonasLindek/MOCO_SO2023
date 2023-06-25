package com.datojo.socialpet.View.overlays

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.datojo.socialpet.ViewModel.Inventory

@Composable
fun ShopOverlay(inventory: Inventory) {
    var shopPopUpControl by remember { mutableStateOf(false) }

    if(shopPopUpControl)
        ShopPopUp({ change -> shopPopUpControl = change }, inventory)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(45.dp, 23.dp)
                .clip(CircleShape)
                .background(Color.LightGray.copy(alpha = .8f))
                .padding(8.dp, 0.dp)
        ) {
            Text(
                text = inventory.currency.value.toString()
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
           TextButton(
               Modifier
                   .size(123.dp, 75.dp),
               {shopPopUpControl = true},
               "Shop")
    }
}