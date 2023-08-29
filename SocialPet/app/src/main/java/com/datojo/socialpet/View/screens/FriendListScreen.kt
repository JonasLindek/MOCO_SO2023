package com.datojo.socialpet.View.screens

import androidx.compose.runtime.Composable
import com.datojo.socialpet.View.overlays.ListOverlay
import com.datojo.socialpet.View.theme.SocialPetTheme
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback

@Composable
fun FriendListScreen(
    popBackStack: () -> Unit,
    contacts: List<String>,
    nearbyDevices: List<EndpointDiscoveryCallback>
) {
    SocialPetTheme {
        ListOverlay(contacts, popBackStack) { 1 + 1 }
    }
}