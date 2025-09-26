package com.project.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.domain.repository.GiftRepository

class GiftViewModelFactory(private val repository: GiftRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GiftViewModel(repository) as T
    }
}