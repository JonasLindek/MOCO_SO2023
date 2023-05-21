package com.datojo.socialpet.ui.overlays

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.datojo.socialpet.AddButton
import com.datojo.socialpet.ContactBar
import com.datojo.socialpet.ContactList

@Composable
fun ListOverlay(contacts: List<String>, onClick: () -> Unit, addContact: () -> Unit) {
    ContactBar(onClick)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 30.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top
    ) {
        ContactList(contacts)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        AddButton(addContact)
    }
}