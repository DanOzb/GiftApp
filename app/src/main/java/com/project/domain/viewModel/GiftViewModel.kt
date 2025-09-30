package com.project.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.data.local.GiftEntity
import com.project.domain.repository.GiftRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GiftViewModel @Inject constructor(
    private val repository: GiftRepository
): ViewModel() {

    val gifts: StateFlow<List<GiftEntity>> = repository.getAllGifts.map {
        it.sortedByDescending { gift -> gift.id }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val favoriteGifts: StateFlow<List<GiftEntity>> = repository.getFavoriteGifts.map {
        it.sortedByDescending { gift -> gift.id }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun addGift() = viewModelScope.launch {
        //TODO: Add method body
    }

    fun deleteGift(giftEntity: GiftEntity) = viewModelScope.launch {
        repository.deleteGift(giftEntity)
    }

    fun updateGift(giftEntity: GiftEntity) = viewModelScope.launch {
        repository.updateGift(giftEntity)
    }
}