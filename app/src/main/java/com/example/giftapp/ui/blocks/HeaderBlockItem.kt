package com.example.giftapp.ui.blocks

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.giftapp.domain.model.HeaderBlock

@Composable
fun HeaderBlockItem(
    block: HeaderBlock,
    modifier: Modifier = Modifier
) {
    Text(
        text = block.text,
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.fillMaxWidth()
    )
}