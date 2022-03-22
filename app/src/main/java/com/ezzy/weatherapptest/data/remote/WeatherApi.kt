package com.ezzy.weatherapptest.data.remote

import com.ezzy.weatherapptest.data.remote.dto.current_weather.CurrentWeatherDto
import com.ezzy.weatherapptest.data.remote.dto.current_weather_hourly.CurrentWeatherHourlyDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/v2.0/current")
    suspend fun getMyLocationWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("key") apiKey: String
    ): CurrentWeatherDto

    @GET("/v2.0/current")
    suspend fun getWeatherByCity(
        @Query("city") city: String,
        @Query("key") apiKey: String,
        @Query("include") include: String? = "minutely"
    ): CurrentWeatherHourlyDto

    @GET("/v2.0/current")
    suspend fun searchWeather(
        @Query("city") city: String,
        @Query("key") apiKey: String,
    ): CurrentWeatherDto


}