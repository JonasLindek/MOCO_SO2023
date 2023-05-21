package com.datojo.socialpet.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.datojo.socialpet.ui.overlays.ListOverlay
import com.datojo.socialpet.ui.theme.SocialPetTheme

@Composable
fun FriendListScreen(navController: NavController) {
    val contacts = listOf("Daniel Sonnenberg", "Tom Küper", "Jonas Lindek")
    SocialPetTheme {
        ListOverlay(contacts, { navController.popBackStack() }, { 1 + 1 })
    }
}