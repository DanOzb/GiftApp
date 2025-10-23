package com.example.giftapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.giftapp.domain.model.GiftItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendGiftScreen(){
    var showAddMenu by remember { mutableStateOf(false) }
    val items = remember { mutableStateListOf<GiftItem>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Send Gift") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddMenu = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }

    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)) {
            GiftItemsList(items)

        }
        AddItemMenu(
            expanded = showAddMenu,
            onDismiss = { showAddMenu = false },
            onAddHeader = { showAddMenu = false; items.add(GiftItem.Header()) },
            onAddMessage = {showAddMenu = false; items.add(GiftItem.Message()) },
            onAddVideo = {showAddMenu = false; items.add(GiftItem.Video()) },
            onAddImage = { showAddMenu = false; items.add(GiftItem.Image()) },
            onAddAudio = { showAddMenu = false; items.add(GiftItem.Audio()) },
            onAddFooter = { showAddMenu = false; items.add(GiftItem.Footer()) }
        )
    }
}



@Composable
private fun GiftItemsList(
    items: List<GiftItem>
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(items, key = { _, item -> item.id }) { index, item ->
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(item.type)
            }
        }
    }
}


@Composable
private fun AddItemMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onAddHeader: () -> Unit,
    onAddMessage: () -> Unit,
    onAddVideo: () -> Unit,
    onAddImage: () -> Unit,
    onAddAudio: () -> Unit,
    onAddFooter: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss
    ) {
        DropdownMenuItem(
            text = { Text("Header") },
            onClick = onAddHeader,
        )
        DropdownMenuItem(
            text = { Text("Message") },
            onClick = onAddMessage,
        )
        DropdownMenuItem(
            text = { Text("Video") },
            onClick = onAddVideo,
        )
        DropdownMenuItem(
            text = { Text("Image") },
            onClick = onAddImage,
        )
        DropdownMenuItem(
            text = { Text("Audio") },
            onClick = onAddAudio,
        )
        DropdownMenuItem(
            text = { Text("Footer") },
            onClick = onAddFooter,
        )
    }
}
