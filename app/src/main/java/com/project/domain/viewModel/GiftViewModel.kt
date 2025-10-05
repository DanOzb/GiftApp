package com.project.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.project.data.local.GiftEntity
import com.project.domain.repository.GiftRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class GiftViewModel @Inject constructor(
    private val repository: GiftRepository,
    private val firestore: FirebaseFirestore,
    private val playerViewModel: PlayerViewModel
): ViewModel() {

    val gifts: StateFlow<List<GiftEntity>> = repository.getAllGifts.map {
        it.sortedByDescending { gift -> gift.id }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

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

    fun loadGift(giftId: Int){
        viewModelScope.launch {
            val gift = repository.fetchRemoteGift(giftId)
            if(gift != null){
                //TODO: uncomment later
                //repository.addGift(repository.toEntity(gift))
            } else {
                //TODO: Gift could not load
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        playerViewModel.player.release()
    }
}