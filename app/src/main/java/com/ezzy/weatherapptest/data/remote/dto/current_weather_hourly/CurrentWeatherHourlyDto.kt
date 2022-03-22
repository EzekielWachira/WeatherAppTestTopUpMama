package com.ezzy.weatherapptest.data.remote.dto.current_weather_hourly

data class CurrentWeatherHourlyDto(
    val count: Int,
    val `data`: List<Data>,
    val minutely: List<Minutely>
)