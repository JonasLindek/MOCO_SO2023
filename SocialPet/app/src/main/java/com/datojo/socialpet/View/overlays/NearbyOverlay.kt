package com.datojo.socialpet.View.overlays

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun NearbyOverlay(nearbyDevices: MutableList<String>) {

    var nearbyPopupControl by remember { mutableStateOf(false) }

    if(nearbyPopupControl)
        NearbyPopup ({ change -> nearbyPopupControl = change }, nearbyDevices)

    Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        TextButton(
            Modifier
                .size(150.dp, 50.dp),
            {nearbyPopupControl = true},
            "Add Contact")
    }
}


