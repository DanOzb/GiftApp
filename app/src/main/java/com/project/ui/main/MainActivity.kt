package com.project.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.project.domain.viewModel.GiftViewModel
import com.project.ui.navigation.MainNavigation
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main Activity class that serves as the entry point for the application.
 *
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: GiftViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainNavigation(viewModel)
        }
    }
}