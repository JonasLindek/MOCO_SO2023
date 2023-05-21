package com.datojo.socialpet.View.overlays

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.datojo.socialpet.StatsViewModel

@Composable
fun MenuOverlay(screenRoutes: List<() -> Unit>, screenNames: List<String>, stats: StatsViewModel) {
    var menuPopUpControl by remember { mutableStateOf(false) }
    var settingsPopUpControl by remember { mutableStateOf(false) }
    var notificationPopUpControl by remember { mutableStateOf(false) }
    var audioPopUpControl by remember { mutableStateOf(false) }
    var aboutPopUpControl by remember { mutableStateOf(false) }

    if(menuPopUpControl)
        MenuPopUp({ change -> menuPopUpControl = change }, { change -> settingsPopUpControl = change }, screenRoutes, screenNames)
    if(settingsPopUpControl)
        SettingsPopUp({ change -> settingsPopUpControl = change }, { change -> menuPopUpControl = change },
            listOf(
                { change -> notificationPopUpControl = change },
                { change -> audioPopUpControl = change },
                { change -> aboutPopUpControl = change }
            ))
    if(notificationPopUpControl)
        NotificationPopUp({ change -> notificationPopUpControl = change }, { change -> settingsPopUpControl = change })
    if(audioPopUpControl)
        AudioPopUp({ change -> audioPopUpControl = change }, { change -> settingsPopUpControl = change })
    if(aboutPopUpControl)
        AboutPopUp({ change -> aboutPopUpControl = change }, { change -> settingsPopUpControl = change })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Bottom
    ) {
        // TODO: Healthbars
        StatusBar(Color.Red, stats.health.value)
        StatusBar(Color.Green, stats.hunger.value)
        StatusBar(Color(.2f,.4f,1f), stats.social.value)
        Row {
            MenuButton { change -> menuPopUpControl = change }
        }
    }
}
