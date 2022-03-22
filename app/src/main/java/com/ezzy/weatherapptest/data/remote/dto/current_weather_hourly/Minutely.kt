package com.ezzy.weatherapptest.data.remote.dto.current_weather_hourly

data class Minutely(
    val precip: Int,
    val snow: Int,
    val temp: Double,
    val timestamp_local: String,
    val timestamp_utc: String,
    val ts: Int
)