package com.datojo.socialpet.View.overlays

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback


@Composable
fun NearbyPopup(onDismiss: (Boolean) -> Unit, nearbyDevices: List<EndpointDiscoveryCallback>) {
    Popup(
        alignment = Alignment.Center,
        onDismissRequest = { onDismiss(false) },
        properties = PopupProperties(
            focusable = true,
        )
    ) {
        Column(
            modifier = Modifier
                .size(360.dp, 620.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray.copy(alpha = 1f)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.padding(4.dp))
            Text(
                text = "Discover Contacts",
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )
            Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(12.dp, 6.dp)
            NearbyList(nearbyDevices)
        }
    }
}
@Composable
fun NearbyList(nearbyDevices: List<EndpointDiscoveryCallback>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(nearbyDevices) { nearbyDevices->
            NearbyDevice(nearbyDevices)
        }
    }
}
@Composable
fun NearbyDevice(devices: EndpointDiscoveryCallback) {
    Row(
        modifier = Modifier
            .drawBehind {
                val borderSize = 2.dp.toPx()
                drawLine(
                    color = Color.LightGray,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = borderSize
                )
            }
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
        }
    }
}