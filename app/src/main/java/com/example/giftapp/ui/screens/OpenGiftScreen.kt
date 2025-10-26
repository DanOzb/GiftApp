package com.example.giftapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.giftapp.domain.model.ContentBlock
import com.example.giftapp.ui.blocks.ContentBlockItem

@Composable
fun OpenGiftScreen(
    contentBlocks: List<ContentBlock>,
    modifier: Modifier = Modifier
) {
    val sortedBlocks = remember(contentBlocks) {
        contentBlocks.sortedBy { it.order }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        sortedBlocks.forEach { block ->
            ContentBlockItem(block = block)
        }
    }
}