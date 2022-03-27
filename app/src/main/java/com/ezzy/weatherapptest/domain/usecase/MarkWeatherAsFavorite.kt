package com.ezzy.weatherapptest.domain.usecase

import com.ezzy.weatherapptest.domain.domain.Weather
import com.ezzy.weatherapptest.domain.repository.WeatherRepository
import javax.inject.Inject

class MarkWeatherAsFavoriteUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(weather: Weather) =
        weatherRepository.updateWeather(weather)
}