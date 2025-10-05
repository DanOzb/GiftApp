package com.project.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.project.data.local.GiftEntity
import com.project.data.local.GiftDao
import com.project.domain.model.RemoteGift
import com.project.domain.repository.GiftRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiftRepositoryImpl @Inject constructor(
    private val dao: GiftDao,
    private val firestore: FirebaseFirestore
): GiftRepository {
    override val getAllGifts: Flow<List<GiftEntity>> = dao.getAllGifts()
    override val getFavoriteGifts: Flow<List<GiftEntity>> = dao.getFavoriteGifts()

    override suspend fun addGift(giftEntity: GiftEntity) = dao.insert(giftEntity)

    override suspend fun updateGift(giftEntity: GiftEntity) = dao.updateGift(giftEntity)

    override suspend fun deleteGift(giftEntity: GiftEntity) = dao.delete(giftEntity)

    override suspend fun toEntity(remoteGift: RemoteGift): GiftEntity {
        return GiftEntity(
            id = remoteGift.id,
            title = remoteGift.title,
            sender = remoteGift.sender,
            timestamp = remoteGift.timestamp.toString(),
            contentBlocks = remoteGift.contentBlocks,
        )
    }

    override suspend fun fetchRemoteGift(giftId: Int): RemoteGift? {
        return try {
            val document = firestore.collection("gifts").document(giftId.toString()).get().await()

            document.toObject<RemoteGift>()
        }catch (e: Exception) {
            null
        }

    }

}