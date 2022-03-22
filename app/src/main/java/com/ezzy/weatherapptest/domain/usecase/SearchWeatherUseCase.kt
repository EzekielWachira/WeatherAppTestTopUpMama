package com.ezzy.weatherapptest.domain.usecase

import com.ezzy.weatherapptest.data.remote.dto.current_weather.CurrentWeatherDto
import com.ezzy.weatherapptest.data.resource.StateWrapper
import com.ezzy.weatherapptest.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
){

    suspend operator fun invoke(
        city: String,
        apiKey: String
    ): Flow<StateWrapper<CurrentWeatherDto>> = flow {
        try {
            emit(StateWrapper.Loading)
            val response = weatherRepository.searchWeather(city, apiKey)
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