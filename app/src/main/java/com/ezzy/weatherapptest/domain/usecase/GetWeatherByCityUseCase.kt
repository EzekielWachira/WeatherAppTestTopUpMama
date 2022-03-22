package com.ezzy.weatherapptest.domain.usecase

import com.ezzy.weatherapptest.data.remote.dto.current_weather_hourly.CurrentWeatherHourlyDto
import com.ezzy.weatherapptest.data.resource.StateWrapper
import com.ezzy.weatherapptest.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetWeatherByCityUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(
        city: String,
        apiKey: String
    ): Flow<StateWrapper<CurrentWeatherHourlyDto>> = flow {
        try {
            emit(StateWrapper.Loading)
            val response = weatherRepository.getWeatherByCity(city, apiKey)
            emit(StateWrapper.Success(response))
        } catch (e: HttpException) {
            emit(StateWrapper.Failure(false, e.code(), e.message ?: "Unexpected error occurred"))
        } catch (e: IOException) {
            emit(
                StateWrapper.Failure(
                    true,
                    null,
                    "Couldn't connect to server, check your internet connection"
                )
            )
        }
    }.flowOn(Dispatchers.IO)
}