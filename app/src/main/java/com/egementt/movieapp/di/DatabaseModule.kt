package com.egementt.movieapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.egementt.movieapp.data.local.AppDatabase
import com.egementt.movieapp.data.local.LocalRepository
import com.egementt.movieapp.data.local.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {



    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase{
        return Room.databaseBuilder(context, AppDatabase::class.java, "movie").build()
    }


    @Provides
    fun provideChannelDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.dao()
    }


    @Provides
    fun provideLocalRepository(movieDao: MovieDao) : LocalRepository = LocalRepository(movieDao)
}