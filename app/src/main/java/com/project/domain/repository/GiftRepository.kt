package com.project.domain.repository

import com.project.data.local.Gift
import kotlinx.coroutines.flow.Flow

interface GiftRepository {
    val getAllLetters: Flow<List<Gift>>
    val getAllPictures: Flow<List<Gift>>
    val getAllVideos: Flow<List<Gift>>
    val getFavLetters: Flow<List<Gift>>
    val getFavPictures: Flow<List<Gift>>
    val getFavVideos: Flow<List<Gift>>

}