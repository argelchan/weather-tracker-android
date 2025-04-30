package com.argel.weathertraker.core.di

import android.content.Context
import androidx.room.Room
import com.argel.weathertraker.framework.database.WeatherTrackerDataBase
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
    fun providesHybridgeDataBase(@ApplicationContext context: Context): WeatherTrackerDataBase =
        Room.databaseBuilder(context, WeatherTrackerDataBase::class.java, WeatherTrackerDataBase.DATABASE_NAME)
            .fallbackToDestructiveMigration(false)
            .build()
    //@Provides
    //@Singleton
    //fun providesRecordingsDao(hybridgeDataBase: WeatherTrackerDataBase) = hybridgeDataBase.recordingsDao

}