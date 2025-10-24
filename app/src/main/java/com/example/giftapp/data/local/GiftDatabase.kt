package com.example.giftapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.giftapp.domain.model.GiftEntity

@Database(entities = [GiftEntity::class], version = 1)
abstract class GiftDatabase : RoomDatabase() {
    abstract fun giftDao() : GiftDao
}