package com.example.giftapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.giftapp.domain.model.GiftEntity
import com.example.giftapp.domain.model.ContentBlocksConverter

@Database(entities = [GiftEntity::class], version = 1)
@TypeConverters(ContentBlocksConverter::class)
abstract class GiftDatabase : RoomDatabase() {
    abstract fun giftDao() : GiftDao
}