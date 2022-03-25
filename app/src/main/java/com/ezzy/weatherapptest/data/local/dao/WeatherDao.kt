package com.ezzy.weatherapptest.data.local.dao

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.room.*
import com.ezzy.weatherapptest.domain.domain.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMultiple(weather: List<Weather>)

    @Query("SELECT * FROM `weather` ORDER BY `isFavorite` DESC LIMIT 20")
    fun getWeather(): PagingSource<Int, Weather>

    @Query("DELETE FROM `weather`")
    suspend fun clearWeather()

    @Update
    suspend fun updateWeather(weather: Weather)

    @Query("SELECT COUNT(`id`) FROM `weather`")
    suspend fun count(): Int

}