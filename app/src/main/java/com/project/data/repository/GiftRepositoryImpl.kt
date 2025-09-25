package com.project.data.repository

import com.project.data.local.Gift
import com.project.data.local.GiftDao
import com.project.domain.repository.GiftRepository
import kotlinx.coroutines.flow.Flow

class GiftRepositoryImpl(private val dao: GiftDao): GiftRepository {
    override val getAllLetters: Flow<List<Gift>> = dao.getAllLetters()
    override val getAllPictures: Flow<List<Gift>> = dao.getAllPictures()
    override val getAllVideos: Flow<List<Gift>> = dao.getAllVideos()
    override val getFavLetters: Flow<List<Gift>> = dao.getFavoriteLetters()
    override val getFavPictures: Flow<List<Gift>> = dao.getFavoritePictures()
    override val getFavVideos: Flow<List<Gift>> = dao.getFavoriteVideos()

}