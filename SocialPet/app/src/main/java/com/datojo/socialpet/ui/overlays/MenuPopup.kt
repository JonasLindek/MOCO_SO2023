package com.datojo.socialpet.ui.overlays

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
import com.datojo.socialpet.PopUpButton

@Composable
fun MenuPopUp(onDismiss: (Boolean) -> Unit, onSettings: (Boolean) -> Unit, screenRoutes: List<() -> Unit>, screenNames: List<String>){
    Popup(
        alignment = Alignment.Center,
        onDismissRequest = { onDismiss(false) },
        properties = PopupProperties(
            focusable = true,
        )
    ) {
        Column(
            modifier = Modifier
                .size(250.dp, 320.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray.copy(alpha = .6f)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.padding(4.dp))

            Text(
                text = "Go Somewhere",
                color = Color.White,
                textAlign = TextAlign.Center
            )

            val modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(12.dp, 6.dp)

            PopUpButton(modifier,
                onClick = {
                    screenRoutes[1]()
                    onDismiss(false) }, name = screenNames[1])

            PopUpButton(modifier,
                onClick = {
                    screenRoutes[2]()
                    onDismiss(false) }, name = screenNames[2])

            PopUpButton(modifier,
                onClick = {
                    screenRoutes[0]()
                    onDismiss(false) }, name = screenNames[0])

            PopUpButton(modifier,
                onClick = {
                    onSettings(true)
                    onDismiss(false) }, name = "Settings")

            Spacer(Modifier.padding(4.dp))
        }
    }
}