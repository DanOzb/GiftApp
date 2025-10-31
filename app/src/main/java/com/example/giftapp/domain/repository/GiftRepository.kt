package com.example.giftapp.domain.repository

import com.example.giftapp.domain.model.GiftEntity
import com.example.giftapp.domain.model.RemoteGift
import kotlinx.coroutines.flow.Flow

interface GiftRepository {
    val getAllGifts: Flow<List<GiftEntity>>
    val getFavoriteGifts: Flow<List<GiftEntity>>

    suspend fun addGift(giftEntity: GiftEntity)
    suspend fun updateGift(giftEntity: GiftEntity)
    suspend fun deleteGift(giftEntity: GiftEntity)

    suspend fun fetchRemoteGift(giftId: String): RemoteGift?
    suspend fun toEntity(remoteGift: RemoteGift): GiftEntity

    suspend fun sendGift(remoteGift: RemoteGift)
}