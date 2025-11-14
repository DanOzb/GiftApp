package com.example.giftapp.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.giftapp.domain.model.ContentBlock
import com.example.giftapp.ui.screen.components.ContentBlockItem
import com.example.giftapp.viewmodel.PlayerViewModel

@Composable
fun OpenGiftScreen(
    playerViewModel: PlayerViewModel,
    contentBlocks: List<ContentBlock>,
    modifier: Modifier = Modifier,
    onExit: () -> Unit
) {
    val sortedBlocks = remember(contentBlocks) {
        contentBlocks.sortedBy { it.order }
    }

    val pagerState = rememberPagerState(pageCount = {sortedBlocks.size})

    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        val block = sortedBlocks[page]
        ContentBlockItem(
            playerViewModel = playerViewModel,
            block = block,
            modifier = Modifier.fillMaxSize()
        )
    }
}