package com.ezzy.weatherapptest.domain.usecase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.ezzy.weatherapptest.domain.repository.WeatherRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetLocalWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    @ExperimentalPagingApi
    @ViewModelScoped
    suspend operator fun invoke() =
        weatherRepository.getLocalWeather().flowOn(Dispatchers.IO)

}