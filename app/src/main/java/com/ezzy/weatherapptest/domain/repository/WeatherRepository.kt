package com.ezzy.weatherapptest.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.ezzy.weatherapptest.data.remote.dto.current_weather.CurrentWeatherDto
import com.ezzy.weatherapptest.data.remote.dto.current_weather_hourly.CurrentWeatherHourlyDto
import com.ezzy.weatherapptest.domain.domain.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getMyLocationWeather(
        latitude: Double,
        longitude: Double,
        apiKey: String
    ): CurrentWeatherDto

    suspend fun getWeatherByCity(
        city: String,
        apiKey: String
    ): CurrentWeatherHourlyDto

    suspend fun searchWeather(
        city: String,
        apiKey: String,
    ): CurrentWeatherDto


    suspend fun getLocalWeather(): Flow<PagingData<Weather>>
}