package com.example.giftapp.fakes

import com.example.giftapp.domain.model.GiftEntity
import com.example.giftapp.domain.model.RemoteGift
import com.example.giftapp.domain.repository.GiftRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class FakeGiftRepository : GiftRepository {

    private val storedGifts = MutableStateFlow<List<GiftEntity>>(emptyList())

    override val getAllGifts: StateFlow<List<GiftEntity>> = storedGifts
    override val getFavoriteGifts =
        storedGifts.map { all -> all.filter { it.favorite } }

    var remoteGifts: MutableMap<String, RemoteGift> = mutableMapOf()
    var remoteGiftIds: List<String> = emptyList()
    var sendGiftReturns: Boolean = true
    var fetchRemoteGiftDelayMs: Long = 0L

    val addedGifts: MutableList<GiftEntity> = mutableListOf()
    val updatedGifts: MutableList<GiftEntity> = mutableListOf()
    val deletedGifts: MutableList<GiftEntity> = mutableListOf()
    val sendGiftCalls: MutableList<RemoteGift> = mutableListOf()
    val deleteRemoteGiftCalls: MutableList<String> = mutableListOf()

    override suspend fun addGift(giftEntity: GiftEntity) {
        addedGifts += giftEntity
        storedGifts.value = storedGifts.value
            .filterNot { it.id == giftEntity.id } + giftEntity
    }

    override suspend fun updateGift(giftEntity: GiftEntity) {
        updatedGifts += giftEntity
        storedGifts.value = storedGifts.value.map {
            if (it.id == giftEntity.id) giftEntity else it
        }
    }

    override suspend fun deleteGift(giftEntity: GiftEntity) {
        deletedGifts += giftEntity
        storedGifts.value = storedGifts.value.filterNot { it.id == giftEntity.id }
    }

    override suspend fun fetchRemoteGift(giftId: String): RemoteGift? {
        if (fetchRemoteGiftDelayMs > 0) {
            kotlinx.coroutines.delay(fetchRemoteGiftDelayMs)
        }
        return remoteGifts[giftId]
    }

    override suspend fun fetchRemoteGiftIds(): List<String> = remoteGiftIds

    override suspend fun toEntity(remoteGift: RemoteGift): GiftEntity = GiftEntity(
        id = remoteGift.id,
        title = remoteGift.title,
        sender = remoteGift.sender,
        timestamp = remoteGift.timestamp,
        contentBlocks = remoteGift.contentBlocks,
    )

    override suspend fun sendGift(remoteGift: RemoteGift): Boolean {
        sendGiftCalls += remoteGift
        return sendGiftReturns
    }

    fun seedGifts(vararg gifts: GiftEntity) {
        storedGifts.value = gifts.toList()
    }

    override suspend fun deleteRemoteGift(giftId: String) {
        deleteRemoteGiftCalls += giftId
        remoteGifts.remove(giftId)
    }
}
