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
import com.example.giftapp.domain.model.GiftEntity
import com.example.giftapp.viewmodel.GiftViewModel
import androidx.compose.runtime.collectAsState
import com.example.giftapp.viewmodel.PlayerViewModel

@Composable
fun HomeScreen(
    giftViewModel: GiftViewModel,
    playerViewModel: PlayerViewModel
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
                giftViewModel.loadAndSaveGift("-1") // Replace with the actual gift ID from firebase
                giftViewModel.getGiftById("-1")
                val gift: GiftEntity? = giftViewModel.openedGift.collectAsState().value
                if(gift != null){
                    OpenGiftScreen(
                        playerViewModel = playerViewModel,
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

