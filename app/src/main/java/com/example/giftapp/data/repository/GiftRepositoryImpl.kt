package com.example.giftapp.data.repository

import com.example.giftapp.data.local.GiftDao
import com.example.giftapp.domain.model.ContentBlocksConverter
import com.example.giftapp.domain.model.GiftEntity
import com.example.giftapp.domain.model.RemoteGift
import com.example.giftapp.domain.repository.GiftRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiftRepositoryImpl @Inject constructor(
    private val dao: GiftDao,
    private val firestore: FirebaseFirestore,
    private val converter: ContentBlocksConverter = ContentBlocksConverter()
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
            timestamp = remoteGift.timestamp,
            contentBlocks = remoteGift.contentBlocks,
        )
    }

    override suspend fun fetchRemoteGift(giftId: String): RemoteGift? {
        return try {
            val document = firestore.collection("gifts").document(giftId).get().await()
            if(!document.exists()) return null

            val data = document.data ?: return null

            RemoteGift(
                id = data["id"] as? String ?: giftId,
                title = data["title"] as? String ?: "",
                sender = data["sender"] as? String ?: "",
                timestamp = data["timestamp"] as? Long ?: 0,
                contentBlocks = converter.toContentBlockList(data["contentBlocks"] as? String ?: "[]")
            )
        }catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun sendGift(remoteGift: RemoteGift) {
        val contentBlocksJsonString = converter.fromContentBlockList(remoteGift.contentBlocks)

        val giftDocument = mapOf(
            "id" to remoteGift.id,
            "title" to remoteGift.title,
            "sender" to remoteGift.sender,
            "timestamp" to remoteGift.timestamp,
            "contentBlocks" to contentBlocksJsonString
        )

        firestore.collection("gifts").document(remoteGift.id)
            .set(giftDocument)
            .await()
    }
}