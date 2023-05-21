package com.datojo.socialpet.View.overlays

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.datojo.socialpet.R

@Composable
fun StatusBar(color: Color, state: Float) {
    Row(
        modifier = Modifier.padding(0.dp, 4.dp)
    ) {
        Image(
            painter= painterResource(id = R.drawable.heart),
            contentDescription = "Heart",
            modifier = Modifier
                .width(28.dp)
                .height(28.dp)
                .zIndex(1f)
        )
        LinearProgressIndicator(
            progress = state,
            color = color,
            modifier = Modifier
                .width(54.dp)
                .padding(0.dp, 12.dp)
                .height(12.dp)
                .absoluteOffset((-10).dp, (-4).dp)
                .border(2.dp, Color.Black, MaterialTheme.shapes.small)
                .clip(MaterialTheme.shapes.small)
        )
    }
}