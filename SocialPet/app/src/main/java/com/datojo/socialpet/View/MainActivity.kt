package com.datojo.socialpet

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.datojo.socialpet.Model.Pet
import com.datojo.socialpet.View.Navigation
import com.datojo.socialpet.View.overlays.BackButton
import com.datojo.socialpet.ViewModel.PetStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.Date
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate


class MainActivity : ComponentActivity() {
    private val stats: PetStatus by viewModels()
    private val cat =
        Pet("Test", "Test", 0, .7f, .5f, .5f, Date(Date().time-360), 0)
    private val contacts = listOf("Jonas Lindek", "Tom Küper", "Daniel Sonnenberg")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            //set stats from storage
            stats.setStats(readStatsFromInternalStorage("petStats", cat))
            Navigation(stats, contacts)
        }

        //calc stat change after time away and update livedata in background
        stats.viewModelScope.launch(Dispatchers.Default) {
            stats.calcStats(true)
            Timer().scheduleAtFixedRate(1000, 1000) {
                stats.calcStats()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        saveStatsToInternalStorage("petStats", stats.saveStats(cat))
    }

    private fun saveStatsToInternalStorage(filename: String, pet: Pet) {
        try {
            val fOut = openFileOutput(filename, Context.MODE_PRIVATE)
            val writer = OutputStreamWriter(fOut)

            writer.write(pet.name + "\n")
            writer.write(pet.breed + "\n")
            writer.write(pet.age.toString() + "\n")
            writer.write(pet.health.toString() + "\n")
            writer.write(pet.hunger.toString() + "\n")
            writer.write(pet.thirst.toString() + "\n")
            writer.write(pet.lastOnline.time.toString() + "\n")
            writer.write(pet.currency.toString() + "\n")

            writer.close()
            fOut.close()
        } catch(e: IOException) {
            e.printStackTrace()
        }
    }

    private fun readStatsFromInternalStorage(filename: String, pet: Pet): Pet{
        try {
            val fIn = openFileInput(filename)
            val streamReader = InputStreamReader(fIn)
            val bufferedReader = BufferedReader(streamReader)

            pet.name = bufferedReader.readLine()
            pet.breed = bufferedReader.readLine()
            pet.age = bufferedReader.readLine().toInt()
            pet.health = bufferedReader.readLine().toFloat()
            pet.hunger = bufferedReader.readLine().toFloat()
            pet.thirst = bufferedReader.readLine().toFloat()
            pet.lastOnline = Date(bufferedReader.readLine().toLong())
            pet.currency = bufferedReader.readLine().toInt()

            bufferedReader.close()
            streamReader.close()
            fIn.close()
        } catch(e: IOException) {
            e.printStackTrace()
        }

        return pet
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