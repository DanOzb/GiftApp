package com.project.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.data.local.Gift
import com.project.domain.model.GiftType
import com.project.domain.repository.GiftRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GiftViewModel(private val repository: GiftRepository): ViewModel() {

    val letters: StateFlow<List<Gift>> = repository.getAllLetters.map {
        it.sortedByDescending { gift -> gift.id }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val pictures: StateFlow<List<Gift>> = repository.getAllPictures.map {
        it.sortedByDescending { gift -> gift.id }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val videos: StateFlow<List<Gift>> = repository.getAllVideos.map {
        it.sortedByDescending { gift -> gift.id }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val favLetters: StateFlow<List<Gift>> = repository.getFavLetters.map {
        it.sortedByDescending { gift -> gift.id }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val favPictures: StateFlow<List<Gift>> = repository.getFavPictures.map {
        it.sortedByDescending { gift -> gift.id }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val favVideos: StateFlow<List<Gift>> = repository.getFavVideos.map {
        it.sortedByDescending { gift -> gift.id }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun addGift(
        type: GiftType,
        content: String,
    ) = viewModelScope.launch {
        repository.addGift(Gift(type = type, content = content))
    }

    fun deleteGift(gift: Gift) = viewModelScope.launch {
        repository.deleteGift(gift)
    }

    fun updateGift(gift: Gift) = viewModelScope.launch {
        repository.updateGift(gift)
    }
}