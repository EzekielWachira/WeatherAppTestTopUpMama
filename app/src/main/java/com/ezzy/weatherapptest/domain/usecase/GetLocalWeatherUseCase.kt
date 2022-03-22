package com.ezzy.weatherapptest.domain.usecase

import androidx.paging.ExperimentalPagingApi
import com.ezzy.weatherapptest.domain.repository.WeatherRepository
import javax.inject.Inject

class GetLocalWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    @ExperimentalPagingApi
    suspend operator fun invoke() = weatherRepository.getLocalWeather()

}