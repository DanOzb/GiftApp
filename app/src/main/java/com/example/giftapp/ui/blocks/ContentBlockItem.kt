package com.example.giftapp.ui.blocks

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import com.example.giftapp.domain.model.AudioBlock
import com.example.giftapp.domain.model.ContentBlock
import com.example.giftapp.domain.model.FooterBlock
import com.example.giftapp.domain.model.HeaderBlock
import com.example.giftapp.domain.model.ImageBlock
import com.example.giftapp.domain.model.TextBlock
import com.example.giftapp.domain.model.VideoBlock
import com.example.giftapp.viewmodel.PlayerViewModel

@Composable
fun ContentBlockItem(
    playerViewModel: PlayerViewModel,
    block: ContentBlock,
    modifier: Modifier = Modifier,
) {
    when (block) {
        is HeaderBlock -> HeaderBlockItem(block = block, modifier = modifier)
        is TextBlock -> TextBlockItem(block = block, modifier = modifier)
        is ImageBlock -> ImageBlockItem(block = block, modifier = modifier)
        is VideoBlock -> VideoBlockItem(playerViewModel = playerViewModel, block = block, modifier = modifier)
        is AudioBlock -> AudioBlockItem(playerViewModel = playerViewModel, block = block, modifier = modifier)
        is FooterBlock -> FooterBlockItem(block = block, modifier = modifier)
    }
}