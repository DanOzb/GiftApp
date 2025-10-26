package com.example.giftapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.giftapp.domain.model.GiftEntity
import com.example.giftapp.viewmodel.GiftViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun HomeScreen(
    viewModel: GiftViewModel = hiltViewModel(),
    ){
    var showGift by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            showGift = true
        }) {
            if(showGift){
                viewModel.loadAndSaveGift("-1") // Replace with the actual gift ID from firebase
                viewModel.getGiftById("-1")
                val gift: GiftEntity? = viewModel.openedGift.collectAsState().value
                if(gift != null){
                    OpenGiftScreen(
                        contentBlocks = gift.contentBlocks,
                        onExit = { showGift = false }
                    )
                } else {
                    Text("Gift not found")
                }
            }
            Text("Open Test Gift")
        }
    }
}

