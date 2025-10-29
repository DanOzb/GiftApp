package com.example.giftapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.giftapp.domain.model.ContentBlocksConverter
import com.example.giftapp.domain.model.GiftItem
import com.example.giftapp.domain.model.giftItemConverter
import com.example.giftapp.ui.screen.components.AddItemMenu
import com.example.giftapp.ui.screen.components.MediaPickerButton
import com.example.giftapp.viewmodel.GiftViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendGiftScreen(
    viewModel: GiftViewModel
){
    var showAddMenu by remember { mutableStateOf(false) }
    val items = remember { mutableStateListOf<GiftItem>() }

    val contentBlocksConverter = remember { ContentBlocksConverter() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Send Gift") },
                actions = {
                    Button(onClick = {
                        val contentblocks = giftItemConverter(items)

                        val jsonString = contentBlocksConverter.fromContentBlockList(contentblocks)

                        //TODO: Add function to send gift to firebase
                        //viewModel.sendGift(jsonString)

                    }) {
                        Text("Send")
                    }
                }
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
            GiftItemsList(
                items = items,
            )
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
    items: MutableList<GiftItem>
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
                when(item){
                    is GiftItem.Image ->{
                        if(item.uri != null){
                            Image(
                                painter = rememberAsyncImagePainter(item.uri),
                                contentDescription = "Selected image",
                                modifier = Modifier.fillMaxWidth(),
                                contentScale = ContentScale.Fit
                            )
                        }
                        MediaPickerButton(
                            mimeType = "image/*",
                            text = "Select image"
                        ) { uri ->
                            items[index] = item.copy(uri = uri)
                        }
                    }
                    is GiftItem.Video -> {
                        //TODO: Tumbnail
                        Text(text = item.uri?.path ?: "No video selected")
                        MediaPickerButton("video/*", "Select Video") { uri ->
                            items[index] = item.copy(uri = uri)
                        }
                    }
                    is GiftItem.Audio -> {
                        Text(text = item.uri?.path ?: "No audio selected")
                        MediaPickerButton("audio/*", "Select Audio") { uri ->
                            items[index] = item.copy(uri = uri)
                        }
                    }
                    is GiftItem.Header -> {
                        OutlinedTextField(
                            value = item.text,
                            onValueChange = { newText ->
                                items[index] = item.copy(text = newText)
                            },
                            label = { Text("Header Text") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    is GiftItem.Message -> {
                        OutlinedTextField(
                            value = item.text,
                            onValueChange = { newText ->
                                items[index] = item.copy(text = newText)
                            },
                            label = { Text("Message Text") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    is GiftItem.Footer -> {
                        OutlinedTextField(
                            value = item.text,
                            onValueChange = { newText ->
                                items[index] = item.copy(text = newText)
                            },
                            label = { Text("Footer Text") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Button(onClick = { items.removeAt(index) }) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "Delete",
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

