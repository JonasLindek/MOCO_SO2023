package com.datojo.socialpet

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewModelScope
import com.datojo.socialpet.Model.AlarmItem
import com.datojo.socialpet.Model.Items
import com.datojo.socialpet.Model.Pet
import com.datojo.socialpet.View.Navigation
import com.datojo.socialpet.View.cancelAlarm
import com.datojo.socialpet.View.predict
import com.datojo.socialpet.ViewModel.Inventory
import com.datojo.socialpet.ViewModel.PetStatus
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.AdvertisingOptions
import com.google.android.gms.nearby.connection.ConnectionInfo
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback
import com.google.android.gms.nearby.connection.ConnectionResolution
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo
import com.google.android.gms.nearby.connection.DiscoveryOptions
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback
import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.connection.PayloadCallback
import com.google.android.gms.nearby.connection.PayloadTransferUpdate
import com.google.android.gms.nearby.connection.Strategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate


class MainActivity : ComponentActivity() {
    private val stats: PetStatus by viewModels()
    private val inventory: Inventory by viewModels()
    private val cat =
        Pet("Test", "Test", 0, 1f, .5f, .5f, Date())
    private val items =
        Items(100, 100, 100)

    private val contacts = listOf("Jonas Lindek", "Tom Küper", "Daniel Sonnenberg")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startAdvertising()
        startDiscovery()
        createNotificationChannel()
        cancelAlarm(applicationContext, AlarmItem(1, "", Date()))
        cancelAlarm(applicationContext, AlarmItem(2, "", Date()))
        cancelAlarm(applicationContext, AlarmItem(3, "", Date()))
        cancelAlarm(applicationContext, AlarmItem(4, "", Date()))

        setContent {
            //set stats from storage
            stats.setStats(cat.readFromInternalStorage("petStats", applicationContext))
            inventory.setItems(items.readFromInternalStorage("inventory", applicationContext))
            Navigation(stats, inventory, contacts)
        }

        //calc stat change after time away and update livedata in background
        stats.viewModelScope.launch(Dispatchers.Default) {
            stats.calcStats(true)
            Timer().scheduleAtFixedRate(1000, 1000) {
                stats.calcStats()
            }
        }
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "STAT_CHANNEL",
                "Status Report",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Used For the Status Reminder notifications"

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun startAdvertising() {
        val advertisingOptions = AdvertisingOptions.Builder().setStrategy(Strategy.P2P_STAR).build()
        Nearby.getConnectionsClient(applicationContext)
            .startAdvertising(
                "socialpet", "com.datojo.socialpet.View", connectionLifecycleCallback, advertisingOptions)
            .addOnSuccessListener { }
            .addOnFailureListener { }
    }

    val payloadCallback: PayloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(p0: String, p1: Payload) {
        }
        override fun onPayloadTransferUpdate(p0: String, p1: PayloadTransferUpdate) {
        }
    }

    val connectionLifecycleCallback: ConnectionLifecycleCallback =
        object : ConnectionLifecycleCallback() {
            override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
                Nearby.getConnectionsClient(applicationContext).acceptConnection(endpointId,payloadCallback)
            }
            override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
                when (result.status.statusCode) {
                    ConnectionsStatusCodes.STATUS_OK -> {}
                    ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED -> {}
                    ConnectionsStatusCodes.STATUS_ERROR -> {}
                    else -> {}
                }
            }
            override fun onDisconnected(endpointId: String) {
            }
        }
    private fun startDiscovery() {
        val discoveryOptions = DiscoveryOptions.Builder().setStrategy(Strategy.P2P_STAR).build()
        Nearby.getConnectionsClient(applicationContext)
            .startDiscovery(
                "socialpet", endpointDiscoveryCallback, discoveryOptions)
            .addOnSuccessListener { }
            .addOnFailureListener { }
    }

    private val endpointDiscoveryCallback: EndpointDiscoveryCallback =
        object : EndpointDiscoveryCallback() {
            override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
                Nearby.getConnectionsClient(applicationContext)
                    .requestConnection("socialpet", endpointId, connectionLifecycleCallback)
                    .addOnSuccessListener { }
                    .addOnFailureListener { }
            }
            override fun onEndpointLost(endpointId: String) {
            }
        }


    override fun onPause() {
        super.onPause()
        predict(stats, applicationContext)
        stats.saveStats(cat).saveToInternalStorage("petStats", applicationContext)
        inventory.saveItems(items).saveToInternalStorage("inventory", applicationContext)
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