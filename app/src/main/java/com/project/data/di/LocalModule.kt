package com.project.data.di

import android.content.Context
import androidx.room.Room
import com.project.data.local.AppDatabase
import com.project.data.local.GiftDao
import com.project.data.repository.GiftRepositoryImpl
import com.project.domain.repository.GiftRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "gift_db"
        ).build()
    }

    @Provides
    fun provideGiftDao(database: AppDatabase): GiftDao {
        return database.giftDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideGiftRepository(database: AppDatabase): GiftRepository {
        return GiftRepositoryImpl(database.giftDao())
    }
}