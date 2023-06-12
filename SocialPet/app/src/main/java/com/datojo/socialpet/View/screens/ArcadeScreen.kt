package com.datojo.socialpet.View.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.datojo.socialpet.Background
import com.datojo.socialpet.ViewModel.PetStatus
import com.datojo.socialpet.R
import com.datojo.socialpet.View.overlays.MenuOverlay
import com.datojo.socialpet.View.theme.SocialPetTheme

@Composable
fun ArcadeScreen(friendListNav: () -> Unit, homeNav: () -> Unit, mallNav: () -> Unit, stats: PetStatus) {
    SocialPetTheme {
        Background(
            R.drawable.arcade, "Arcade",
            Modifier
                .scale(3.5f)
                .fillMaxSize()
                .padding(0.dp, 275.dp)
        )
        MenuOverlay(
            listOf(friendListNav, homeNav, mallNav),
            listOf("Friends", "Home", "Mall"),
            stats
        )
    }
}