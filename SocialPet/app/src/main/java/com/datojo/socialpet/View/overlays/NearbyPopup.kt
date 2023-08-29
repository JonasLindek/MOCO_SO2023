package com.datojo.socialpet.View.overlays

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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



@Composable
fun NearbyPopup(onDismiss: (Boolean) -> Unit) {
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
        }
    }
}

