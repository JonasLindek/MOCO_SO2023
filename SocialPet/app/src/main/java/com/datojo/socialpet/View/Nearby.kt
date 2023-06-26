package com.datojo.socialpet.View

import android.Manifest
import android.os.Build
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


val requiredPermissions =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        arrayOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_ADVERTISE,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.ACCESS_FINE_LOCATION)
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    } else {
        arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)
    }


private fun startAdvertising(){
    connectionsClient.startAdvertising("socialPet", packageName, connectionLifecycleCallback,
        AdvertisingOptions.Builder().setStrategy(Strategy.P2P_STAR).build())
    // Das Device wird nun anderen Devices, welche discovern angezeigt
}


private fun startDiscovery(){
    val endpointDiscoveryCallback:EndpointDiscoveryCallback =
        object:EndpointDiscoveryCallback(){
            override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
                connectionsClient.requestConnection("socialPet", endpointId, connectionLifecycleCallback)
                // Ein Endpoint wurde gefunden und es wird versucht eine Verbindung herzustellen
            }
            override fun onEndpointLost(endpointId: String) {
            }
        }
    connectionsClient.startDiscovery(packageName,endpointDiscoveryCallback,
        DiscoveryOptions.Builder().setStrategy(Strategy.P2P_STAR).build())
}


private val connectionLifecycleCallback = object:ConnectionLifecycleCallback(){
    override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
        connectionsClient.acceptConnection(endpointId,payloadCallback)
        friendCodeName = connectionInfo.endpointName
    }
    override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
        when (result.status.statusCode) {
            ConnectionsStatusCodes.STATUS_OK -> {
                // Verbindung wurde hergestellt
                connectionsClient.stopDiscovery();
                connectionsClient.stopAdvertising()
                friendEndpointId = endpointId
            }
            ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED -> {
                // Die Verbindung wurde von einem oder beiden Devices nicht akzeptiert
            }
            ConnectionsStatusCodes.STATUS_ERROR -> {
                // Die Devices konnten sich nicht verbinden
            }
            else -> {
            }
        }
    }
    override fun onDisconnected(endpointId: String) {
    }
}

