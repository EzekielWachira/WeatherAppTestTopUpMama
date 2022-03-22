package com.ezzy.weatherapptest.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ezzy.weatherapptest.data.local.WeatherDatabase
import com.ezzy.weatherapptest.data.local.dao.RemoteKeyDao
import com.ezzy.weatherapptest.data.local.dao.WeatherDao
import com.ezzy.weatherapptest.data.remote.WeatherApi
import com.ezzy.weatherapptest.data.remote.dto.current_weather.CurrentWeatherDto
import com.ezzy.weatherapptest.data.remote.dto.current_weather_hourly.CurrentWeatherHourlyDto
import com.ezzy.weatherapptest.data.remote.mediator.RemoteWeatherMediator
import com.ezzy.weatherapptest.domain.domain.Weather
import com.ezzy.weatherapptest.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao,
    private val remoteKeyDao: RemoteKeyDao,
    private val weatherDatabase: WeatherDatabase
) : WeatherRepository {

    override suspend fun getMyLocationWeather(
        latitude: Double,
        longitude: Double,
        apiKey: String
    ): CurrentWeatherDto {
        return weatherApi.getMyLocationWeather(latitude, longitude, apiKey)
    }

    override suspend fun getWeatherByCity(city: String, apiKey: String): CurrentWeatherHourlyDto {
        return weatherApi.getWeatherByCity(city, apiKey)
    }

    override suspend fun searchWeather(city: String, apiKey: String): CurrentWeatherDto {
        return weatherApi.searchWeather(city, apiKey)
    }

    @ExperimentalPagingApi
    override suspend fun getLocalWeather(): Flow<PagingData<Weather>> {
        val pagingSource = { weatherDao.getWeather() }
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = RemoteWeatherMediator(
                weatherApi, weatherDatabase, weatherDao, remoteKeyDao, ""
            ),
            pagingSourceFactory = pagingSource
        ).flow
    }
}