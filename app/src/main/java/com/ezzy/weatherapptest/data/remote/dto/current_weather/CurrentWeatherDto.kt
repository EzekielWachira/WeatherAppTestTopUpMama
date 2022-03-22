package com.ezzy.weatherapptest.data.remote.dto.current_weather

data class CurrentWeatherDto(
    val count: Int,
    val `data`: List<WeatherData>
)