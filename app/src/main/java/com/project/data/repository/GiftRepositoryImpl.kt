package com.project.data.repository

import com.project.data.local.Gift
import com.project.data.local.GiftDao
import com.project.domain.repository.GiftRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiftRepositoryImpl @Inject constructor(private val dao: GiftDao): GiftRepository {
    override val getAllLetters: Flow<List<Gift>> = dao.getAllLetters()
    override val getAllPictures: Flow<List<Gift>> = dao.getAllPictures()
    override val getAllVideos: Flow<List<Gift>> = dao.getAllVideos()
    override val getFavLetters: Flow<List<Gift>> = dao.getFavoriteLetters()
    override val getFavPictures: Flow<List<Gift>> = dao.getFavoritePictures()
    override val getFavVideos: Flow<List<Gift>> = dao.getFavoriteVideos()

    override suspend fun addGift(gift: Gift) = dao.insert(gift)

    override suspend fun updateGift(gift: Gift) = dao.updateGift(gift)

    override suspend fun deleteGift(gift: Gift) = dao.delete(gift)

}