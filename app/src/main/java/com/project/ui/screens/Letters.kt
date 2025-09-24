package com.project.ui.screens

import androidx.compose.runtime.Composable
import com.project.model.GiftType

/**
 * Screen composable that calls LazyGrid with gift type letter
 */
@Composable
fun LettersScreen(){
    LazyGrid(GiftType.LETTER)
}