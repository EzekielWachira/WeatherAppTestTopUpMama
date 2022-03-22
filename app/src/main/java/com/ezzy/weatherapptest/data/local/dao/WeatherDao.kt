package com.ezzy.weatherapptest.data.local.dao

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ezzy.weatherapptest.domain.domain.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMultiple(weather: List<Weather>)

    @Query("SELECT * FROM `weather` ORDER BY `isFavorite` DESC")
    fun getWeather(): PagingSource<Int, Weather>

    @Query("DELETE FROM `weather`")
    suspend fun clearWeather()

    @Query("SELECT COUNT(`id`) FROM `weather`")
    suspend fun count(): Int

}