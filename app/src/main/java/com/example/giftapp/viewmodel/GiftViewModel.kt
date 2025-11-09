package com.example.giftapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giftapp.domain.model.GiftEntity
import com.example.giftapp.domain.model.RemoteGift
import com.example.giftapp.domain.repository.GiftRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val currentGift = MutableStateFlow<GiftEntity?>(null)
    val openedGift: StateFlow<GiftEntity?> = currentGift.asStateFlow()

    private val _sendGiftResult = MutableStateFlow<Boolean?>(null)
    val sendGiftResult = _sendGiftResult.asStateFlow()

    val favoriteGifts: StateFlow<List<GiftEntity>> = repository.getFavoriteGifts.map {
        it.sortedByDescending { gift -> gift.id }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun addGift(giftEntity: GiftEntity) = viewModelScope.launch {
        repository.addGift(giftEntity)
    }

    fun deleteGift(giftEntity: GiftEntity) = viewModelScope.launch {
        repository.deleteGift(giftEntity)
    }

    fun updateGift(giftEntity: GiftEntity) = viewModelScope.launch {
        repository.updateGift(giftEntity)
    }

    fun loadAndSaveGift(giftId: String) {
        viewModelScope.launch {
            val remoteGift = repository.fetchRemoteGift(giftId)
            if (remoteGift != null) {
                val entity = repository.toEntity(remoteGift)
                repository.addGift(entity)
            } else {
                println("Error: Gift not found")
            }
        }
    }

    fun getGiftById(giftId: String) {
        viewModelScope.launch {
            repository.getAllGifts.map { giftList ->
                giftList.find { it.id == giftId }
            }.collect { gift ->
                currentGift.value = gift
            }
        }
    }

    fun sendGift(remoteGift: RemoteGift){
        viewModelScope.launch {
            val success = repository.sendGift(remoteGift)
            _sendGiftResult.value = success
        }
    }

    fun resetSendGiftResult(){
        _sendGiftResult.value = null
    }
}