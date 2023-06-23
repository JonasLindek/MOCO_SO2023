package com.datojo.socialpet.View.overlays

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun MenuButton(onClick: (Boolean) -> Unit) {
    Row(){
        FloatingActionButton(
            onClick = { onClick(true) } ,
            shape = CircleShape,
            containerColor = Color.White,
            modifier = Modifier
                .width(75.dp)
                .height(75.dp)
        ) {

        }
    }
}

@Composable
fun BackButton(onClick: () -> Unit) {
    Row (){
        FloatingActionButton(
            onClick = onClick ,
            shape = CircleShape,
            containerColor = Color.White,
            modifier = Modifier
                .width(20.dp)
                .height(20.dp)
        ) {

        }
    }
}

@Composable
fun AddButton(onClick: () -> Unit) {
    Row () {
        FloatingActionButton(
            onClick = onClick,
            shape = CircleShape,
            containerColor = Color.White,
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
        ) {

        }
    }
}

@Composable
fun TextButton(modifier: Modifier = Modifier, onClick: () -> Unit, name: String) {
    Row() {
        Button(
            modifier = modifier,
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray.copy(alpha = .8f)),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(
                text = name,
                color = Color.White
            )
        }
    }
}

@Composable
fun CatInteraction(onClick: () -> Unit, id: Int) {
    Row(){
        FloatingActionButton(
            onClick = { onClick() } ,
            shape = CircleShape,
            containerColor = Color.White,
            modifier = Modifier
                .width(75.dp)
                .height(75.dp)
        ) {
            Image(
                painter = painterResource(id),
                contentDescription = null,
                modifier = Modifier
                    .padding(0.dp, 5.dp, 0.dp, 20.dp)
            )
        }
    }
}