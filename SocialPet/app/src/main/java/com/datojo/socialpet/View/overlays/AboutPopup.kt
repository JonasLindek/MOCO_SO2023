package com.datojo.socialpet.View.overlays

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
fun AboutPopUp(onDismiss: (Boolean) -> Unit, onBack: (Boolean) -> Unit) {
    Popup(
        alignment = Alignment.Center,
        onDismissRequest = { onDismiss(false) },
        properties = PopupProperties(
            focusable = true,
        )
    ) {
        Column(
            modifier = Modifier
                .size(150.dp, 150.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray.copy(alpha = .6f)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.padding(4.dp))

            Text(
                text = "About",
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Tobias Peltzer \n" + "Technical Support \n" + "Lorem Ipsum",
                color = Color.White,
                textAlign = TextAlign.Left
            )

            PopUpButton(
                Modifier
                    .width(110.dp)
                    .height(50.dp)
                    .padding(12.dp, 6.dp),
                onClick = {
                    onBack(true)
                    onDismiss(false) }, "Back")

            Spacer(Modifier.padding(4.dp))
        }
    }
}