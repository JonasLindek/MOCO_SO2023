package com.datojo.socialpet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.datojo.socialpet.ui.Navigation
import com.datojo.socialpet.ui.Screen
import com.datojo.socialpet.ui.theme.SocialPetTheme
import kotlinx.coroutines.delay



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val stats: StatsViewModel by viewModels()
        setContent {
            stats.setStats(.75f, .5f, .5f)
            Navigation(stats)
        }
    }
}

@Composable
fun Background(id: Int, description: String, modifier: Modifier) {
    Image(
        painter = painterResource(id = id),
        contentDescription = description,
        modifier = modifier
    )
}

@Composable
fun Contact(name: String) {
    val hex = "#%06x".format(0xFFFFFF and name.hashCode())

    Row(
        modifier = Modifier
            .drawBehind {
                val borderSize = 2.dp.toPx()
                drawLine(
                    color = Color.LightGray,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = borderSize
                )
            }
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color(android.graphics.Color.parseColor(hex)))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Text(text = name)
        }
    }

}
@Composable
fun ContactList(contacts: List<String>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(contacts) { contacts->
            Contact(contacts)
        }
    }
}

@Composable
fun ContactBar(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.Start
        ){
            BackButton(onClick)
        }

        Row(
            modifier = Modifier
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Contacts",
                textAlign = TextAlign.Center,
            )
        }
    }
}


//Button Composables
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
fun PopUpButton(modifier: Modifier = Modifier, onClick: () -> Unit, name: String) {
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
fun CatInteraction(onClick: () -> Unit) {
    Row(){
        FloatingActionButton(
            onClick = { onClick() } ,
            shape = CircleShape,
            containerColor = Color.White,
            modifier = Modifier
                .width(75.dp)
                .height(75.dp)
        ) {

        }
    }
}