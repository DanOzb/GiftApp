package com.example.giftapp.ui.screen.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import com.example.giftapp.domain.model.VideoBlock
import com.example.giftapp.viewmodel.PlayerViewModel

@Composable
fun VideoBlockItem(
    playerViewModel: PlayerViewModel,
    block: VideoBlock,
    modifier: Modifier = Modifier
) {
    val exoPlayer = playerViewModel.player

    LaunchedEffect(block.url) {
        playerViewModel.playMedia(block.url)
    }

    Card(
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(16/9f),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    this.player = exoPlayer
                    useController = true
                }
            },
            update = { playerView ->
                playerView.player = exoPlayer
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}