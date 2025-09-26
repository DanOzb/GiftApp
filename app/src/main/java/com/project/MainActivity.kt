package com.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.project.data.local.AppDatabase
import com.project.data.repository.GiftRepositoryImpl
import com.project.domain.viewModel.GiftViewModel
import com.project.domain.viewModel.GiftViewModelFactory
import com.project.ui.navigation.MainNavigation

/**
 * Main Activity class that serves as the entry point for the application.
 *
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO: Change later to dependency injection
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "gift_db"
        ).build()

        val repository = GiftRepositoryImpl(db.giftDao())
        val factory = GiftViewModelFactory(repository)

        setContent {
            val viewModel: GiftViewModel = viewModel(factory = factory)
            MainNavigation(viewModel)
        }
    }
}

