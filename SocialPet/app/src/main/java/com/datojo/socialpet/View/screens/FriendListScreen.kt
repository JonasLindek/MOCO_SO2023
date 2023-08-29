package com.datojo.socialpet.View.screens

import androidx.compose.runtime.Composable
import com.datojo.socialpet.View.overlays.ListOverlay
import com.datojo.socialpet.View.theme.SocialPetTheme

@Composable
fun FriendListScreen(
    popBackStack: () -> Unit,
    contacts: List<String>,
    nearbyDevices: MutableList<String>
) {
    SocialPetTheme {
        ListOverlay(contacts,nearbyDevices , popBackStack)
    }
}