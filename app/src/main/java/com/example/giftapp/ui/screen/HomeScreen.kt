package com.example.giftapp.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.giftapp.viewmodel.GiftViewModel
import com.example.giftapp.viewmodel.PlayerViewModel


@Composable
fun HomeScreen(
    giftViewModel: GiftViewModel,
    playerViewModel: PlayerViewModel
) {
    var giftToOpenId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        giftViewModel.syncGiftIDs()
    }

    val remoteGiftIds by giftViewModel.remoteGiftIds.collectAsState()
    val openedGift by giftViewModel.openedGift.collectAsState()

    LaunchedEffect(giftToOpenId) {
        if (giftToOpenId != null) {
            giftViewModel.loadAndSaveGift(giftToOpenId!!).join()
        }
    }

    val isLoading = giftToOpenId != null && openedGift?.id != giftToOpenId

    Log.d("HomeScreen", "giftToOpenId: $giftToOpenId, openedGiftId: ${openedGift?.id}")

    if (!isLoading && openedGift?.id == giftToOpenId && openedGift != null) {
        OpenGiftScreen(
            playerViewModel = playerViewModel,
            contentBlocks = openedGift!!.contentBlocks,
            onExit = {
                giftToOpenId = null
                giftViewModel.clearOpenedGift()
            }
        )
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("You have ${remoteGiftIds.size} new gifts waiting!")
                    Button(
                        modifier = Modifier.padding(top = 16.dp),
                        enabled = remoteGiftIds.isNotEmpty(),
                        onClick = {
                            if(remoteGiftIds.isNotEmpty())
                                giftToOpenId = remoteGiftIds.first()
                        }
                    ) {
                        Text("Open a Gift")
                    }
                }
            }
        }
    }
}

