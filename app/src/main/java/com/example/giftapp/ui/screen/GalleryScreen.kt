package com.example.giftapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.giftapp.viewmodel.GiftViewModel
import com.example.giftapp.viewmodel.PlayerViewModel


@Composable
fun GalleryScreen(
    giftViewModel: GiftViewModel,
    playerViewModel: PlayerViewModel,
){
    val items = giftViewModel.gifts.collectAsState().value
    var openIndex by remember { mutableStateOf<Int?>(null) }
    if (openIndex != null){
        OpenGiftScreen(
            playerViewModel = playerViewModel,
            contentBlocks = items[openIndex!!].contentBlocks,
            onExit = { openIndex = null }
        )
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 100.dp),
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(items.size) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { openIndex = index }
                        .padding(8.dp)
                ) {
                    //TODO:add thumbnail later
                    Text(items[index].title)
                }
            }
        }
    }

}
