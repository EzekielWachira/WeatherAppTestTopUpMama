package com.ezzy.weatherapptest.di

import android.content.Context
import androidx.room.Room
import com.ezzy.weatherapptest.data.local.WeatherDatabase
import com.ezzy.weatherapptest.data.local.dao.RemoteKeyDao
import com.ezzy.weatherapptest.data.local.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideWeatherDatabaseDatabase(
        @ApplicationContext context: Context
    ): WeatherDatabase {
        return synchronized(this) {
            Room.databaseBuilder(
                context,
                WeatherDatabase::class.java,
                "weather_db"
            ).fallbackToDestructiveMigration()
                .build()
        }
    }

    @Provides
    @Singleton
    fun provideWeatherDao(database: WeatherDatabase): WeatherDao = database.weatherDao()

    @Provides
    @Singleton
    fun provideRemoteKeyDao(database: WeatherDatabase): RemoteKeyDao = database.remoteKeysDao()

}