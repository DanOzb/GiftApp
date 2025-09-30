package com.project.domain.repository

import com.project.data.local.GiftEntity
import kotlinx.coroutines.flow.Flow

interface GiftRepository {
    val getAllGifts: Flow<List<GiftEntity>>
    val getFavoriteGifts: Flow<List<GiftEntity>>

    suspend fun addGift(giftEntity: GiftEntity)
    suspend fun updateGift(giftEntity: GiftEntity)
    suspend fun deleteGift(giftEntity: GiftEntity)
}