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
import com.example.giftapp.domain.model.AudioBlock
import com.example.giftapp.domain.model.ContentBlock
import com.example.giftapp.domain.model.FooterBlock
import com.example.giftapp.domain.model.HeaderBlock
import com.example.giftapp.domain.model.ImageBlock
import com.example.giftapp.domain.model.RemoteGift
import com.example.giftapp.domain.model.TextBlock
import com.example.giftapp.domain.model.VideoBlock
import com.example.giftapp.ui.screen.components.AddItemMenu
import com.example.giftapp.ui.screen.components.MediaPickerButton
import com.example.giftapp.viewmodel.GiftViewModel
import com.google.firebase.auth.FirebaseAuth


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendGiftScreen(
    viewModel: GiftViewModel
){
    var showAddMenu by remember { mutableStateOf(false) }
    val items = remember { mutableStateListOf<ContentBlock>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Send Gift") },
                actions = {
                    Button(onClick = {

                        val remoteGift = RemoteGift(
                            title = "Gift",
                            sender = FirebaseAuth.getInstance().currentUser?.uid ?: "-",
                            contentBlocks = items
                        )
                        viewModel.sendGift(remoteGift)
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
            onAddHeader = { showAddMenu = false; items.add( HeaderBlock(order = items.size)) },
            onAddMessage = {showAddMenu = false; items.add(TextBlock(order = items.size)) },
            onAddVideo = {showAddMenu = false; items.add(VideoBlock(order = items.size)) },
            onAddImage = { showAddMenu = false; items.add(ImageBlock(order = items.size)) },
            onAddAudio = { showAddMenu = false; items.add(AudioBlock(order = items.size)) },
            onAddFooter = { showAddMenu = false; items.add(FooterBlock(order = items.size)) }
        )
    }
}

@Composable
private fun GiftItemsList(
    items: MutableList<ContentBlock>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(items, key = { _, item -> item.order }) { index, item ->
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                when(item){
                    is ImageBlock ->{
                        if(item.url.isNotBlank()){
                            Image(
                                painter = rememberAsyncImagePainter(item.url),
                                contentDescription = "Selected image",
                                modifier = Modifier.fillMaxWidth(),
                                contentScale = ContentScale.Fit
                            )
                        }
                        MediaPickerButton(
                            mimeType = "image/*",
                            text = "Select image"
                        ) { uri ->
                            items[index] = item.copy(url = uri)
                        }
                    }
                    is VideoBlock -> {
                        //TODO: Tumbnail
                        if(item.url.isBlank())
                            Text(text = "No video selected")
                        MediaPickerButton("video/*", "Select Video") { uri ->
                            items[index] = item.copy(url = uri)
                        }
                    }
                    is AudioBlock -> {
                        if(item.url.isBlank())
                            Text(text = "No audio selected")
                        MediaPickerButton("audio/*", "Select Audio") { uri ->
                            items[index] = item.copy(url = uri)
                        }
                    }
                    is HeaderBlock -> {
                        OutlinedTextField(
                            value = item.text,
                            onValueChange = { newText ->
                                items[index] = item.copy(text = newText)
                            },
                            label = { Text("Header Text") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    is TextBlock -> {
                        OutlinedTextField(
                            value = item.text,
                            onValueChange = { newText ->
                                items[index] = item.copy(text = newText)
                            },
                            label = { Text("Message Text") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    is FooterBlock -> {
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

