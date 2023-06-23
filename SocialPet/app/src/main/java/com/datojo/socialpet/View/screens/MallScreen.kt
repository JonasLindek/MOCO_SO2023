package com.datojo.socialpet.View.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.datojo.socialpet.Background
import com.datojo.socialpet.View.overlays.MenuOverlay
import com.datojo.socialpet.R
import com.datojo.socialpet.View.overlays.ShopOverlay
import com.datojo.socialpet.ViewModel.PetStatus
import com.datojo.socialpet.View.theme.SocialPetTheme
import com.datojo.socialpet.ViewModel.Inventory

@Composable
fun MallScreen(friendListNav: () -> Unit, homeNav: () -> Unit, arcadeNav: () -> Unit, stats: PetStatus, inventory: Inventory) {
    SocialPetTheme {
        Background(
            R.drawable.mall, "Mall",
            Modifier
                .scale(3.5f)
                .fillMaxSize()
                .padding(0.dp, 275.dp)
        )
        MenuOverlay(
            listOf(friendListNav, homeNav, arcadeNav),
            listOf("Friends", "Home", "Arcade"),
            stats
        )
        ShopOverlay(inventory)
    }
}