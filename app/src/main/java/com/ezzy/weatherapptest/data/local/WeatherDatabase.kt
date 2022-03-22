package com.ezzy.weatherapptest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ezzy.weatherapptest.data.local.dao.RemoteKeyDao
import com.ezzy.weatherapptest.data.local.dao.WeatherDao
import com.ezzy.weatherapptest.data.remote.mediator.RemoteKeys
import com.ezzy.weatherapptest.domain.domain.Weather

@Database(
    entities = [
        Weather::class,
        RemoteKeys::class
    ],
    version = 1,
    exportSchema = true
)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
    abstract fun remoteKeysDao(): RemoteKeyDao

}