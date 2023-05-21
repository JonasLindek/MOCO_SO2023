package com.datojo.socialpet.View.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.datojo.socialpet.View.overlays.ListOverlay
import com.datojo.socialpet.View.theme.SocialPetTheme

@Composable
fun FriendListScreen(navController: NavController) {
    val contacts = listOf("Daniel Sonnenberg", "Tom Küper", "Jonas Lindek")
    SocialPetTheme {
        ListOverlay(contacts, { navController.popBackStack() }, { 1 + 1 })
    }
}