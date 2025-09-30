package com.project.data.repository

import com.project.data.local.GiftEntity
import com.project.data.local.GiftDao
import com.project.domain.model.RemoteGift
import com.project.domain.repository.GiftRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiftRepositoryImpl @Inject constructor(private val dao: GiftDao): GiftRepository {
    override val getAllGifts: Flow<List<GiftEntity>> = dao.getAllGifts()
    override val getFavoriteGifts: Flow<List<GiftEntity>> = dao.getFavoriteGifts()

    override suspend fun addGift(giftEntity: GiftEntity) = dao.insert(giftEntity)

    override suspend fun updateGift(giftEntity: GiftEntity) = dao.updateGift(giftEntity)

    override suspend fun deleteGift(giftEntity: GiftEntity) = dao.delete(giftEntity)

    fun RemoteGift.toEntity(): GiftEntity {
        return GiftEntity(
            id = this.id,
            title = this.title,
            sender = this.sender,
            timestamp = this.timestamp,
            contentBlocks = this.contentBlocks,
        )
    }

}