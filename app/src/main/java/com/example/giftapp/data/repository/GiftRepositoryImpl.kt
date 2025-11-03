package com.example.giftapp.data.repository

import com.example.giftapp.data.local.GiftDao
import com.example.giftapp.domain.model.ContentBlocksConverter
import com.example.giftapp.domain.model.GiftEntity
import com.example.giftapp.domain.model.ImageBlock
import com.example.giftapp.domain.model.RemoteGift
import com.example.giftapp.domain.repository.GiftRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.net.toUri
import com.example.giftapp.domain.model.AudioBlock
import com.example.giftapp.domain.model.VideoBlock

@Singleton
class GiftRepositoryImpl @Inject constructor(
    private val dao: GiftDao,
): GiftRepository {
    private val converter: ContentBlocksConverter = ContentBlocksConverter()
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
            val firestore = FirebaseFirestore.getInstance()

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


    //contentBlocks will have local uris by this point, so
    //we are uploading them to firebase storage and getting their urls assigned
    override suspend fun sendGift(remoteGift: RemoteGift) {
        try {
            for (contentBlock in remoteGift.contentBlocks) {
                if (contentBlock is ImageBlock) {
                    val uploadedUrl = uploadMedia(contentBlock.url)
                    contentBlock.url = uploadedUrl
                }
                if (contentBlock is VideoBlock) {
                    val uploadedUrl = uploadMedia(contentBlock.url)
                    contentBlock.url = uploadedUrl
                }
                if (contentBlock is AudioBlock) {
                    val uploadedUrl = uploadMedia(contentBlock.url)
                    contentBlock.url = uploadedUrl
                }
            }

            val contentBlocksJsonString = converter.fromContentBlockList(remoteGift.contentBlocks)

            val giftDocument = mapOf(
                "id" to remoteGift.id,
                "title" to remoteGift.title,
                "sender" to remoteGift.sender,
                "timestamp" to remoteGift.timestamp,
                "contentBlocks" to contentBlocksJsonString
            )

            val firestore = FirebaseFirestore.getInstance()

            firestore.collection("gifts").document(remoteGift.id)
                .set(giftDocument)
                .await()
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    private suspend fun uploadMedia(
        uri: String,
        folderPath: String = "gifts/",
    ): String {
        return try {
            val storage = FirebaseStorage.getInstance()
            val filename = UUID.randomUUID().toString()
            val ref = storage.reference.child("$folderPath$filename")

            ref.putFile(uri.toUri()).await()

            ref.downloadUrl.await().toString()
        } catch (e: Exception) {
            print("GiftRepository failed to upload media: $uri")
            e.printStackTrace()
            throw e
        }
    }
}