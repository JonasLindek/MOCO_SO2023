package com.datojo.socialpet.View.overlays

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.datojo.socialpet.ViewModel.Inventory

@Composable
fun ShopPopUp(onDismiss: (Boolean) -> Unit, inventory: Inventory) {
    Popup(
        alignment = Alignment.Center,
        onDismissRequest = { onDismiss(false) },
        properties = PopupProperties(
            focusable = true,
        )
    ) {
        Column(
            modifier = Modifier
                .size(270.dp, 200.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray.copy(alpha = .6f)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.padding(4.dp))

            val modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(12.dp, 6.dp)

            Text(
                text = "Money: " + inventory.currency.value.toString(),
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Row() {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Food",
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Inventory: " + inventory.food.value.toString(),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Cost: 10",
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                TextButton(modifier,
                    onClick = {
                              if (inventory.enoughCurrency(10)) {
                                  inventory.addFood()
                                  inventory.subCurrency(10)
                              } }, "Buy")
            }

            Row() {
                Column(
                    modifier = Modifier
                        .padding(8.dp),
                ) {
                    Text(
                        text = "Water",
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Inventory: " + inventory.water.value.toString(),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Cost: 7",
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                TextButton(modifier,
                    onClick = {
                        if (inventory.enoughCurrency(7)) {
                            inventory.addWater()
                            inventory.subCurrency(7)
                        } }, "Buy")
            }

            Spacer(Modifier.padding(4.dp))
        }
    }
}