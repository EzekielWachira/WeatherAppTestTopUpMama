package com.ezzy.weatherapptest.presentation.ui.fragments.weather_main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.ezzy.weatherapptest.data.remote.dto.current_weather.CurrentWeatherDto
import com.ezzy.weatherapptest.data.remote.dto.current_weather_hourly.CurrentWeatherHourlyDto
import com.ezzy.weatherapptest.data.resource.StateWrapper
import com.ezzy.weatherapptest.domain.domain.Weather
import com.ezzy.weatherapptest.domain.usecase.GetLocalWeatherUseCase
import com.ezzy.weatherapptest.domain.usecase.GetMyLocationWeatherUseCase
import com.ezzy.weatherapptest.domain.usecase.GetWeatherByCityUseCase
import com.ezzy.weatherapptest.domain.usecase.SearchWeatherUseCase
import com.ezzy.weatherapptest.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getMyLocationWeatherUseCase: GetMyLocationWeatherUseCase,
    private val searchWeatherUseCase: SearchWeatherUseCase,
    private val getWeatherByCityUseCase: GetWeatherByCityUseCase,
    private val getLocalWeatherUseCase: GetLocalWeatherUseCase
) : ViewModel() {

    private var _myLocationWeatherState: MutableLiveData<StateWrapper<CurrentWeatherDto>> =
        MutableLiveData()
    private var _searchState: MutableLiveData<StateWrapper<CurrentWeatherDto>> =
        MutableLiveData()
    private var _weatherState: MutableLiveData<StateWrapper<CurrentWeatherHourlyDto>> =
        MutableLiveData()

    private var _localWeatherState: SingleLiveEvent<StateWrapper<PagingData<Weather>>> =
        SingleLiveEvent()

    private var _cityWeatherState: MutableLiveData<StateWrapper<CurrentWeatherHourlyDto>> =
        MutableLiveData()

    val myLocationWeatherState: LiveData<StateWrapper<CurrentWeatherDto>> get() = _myLocationWeatherState
    val searchState: LiveData<StateWrapper<CurrentWeatherDto>> get() = _searchState
    val weatherState: LiveData<StateWrapper<CurrentWeatherHourlyDto>> get() = _weatherState
    val localWeatherState: LiveData<StateWrapper<PagingData<Weather>>> = _localWeatherState
    val cityWeatherState: LiveData<StateWrapper<CurrentWeatherHourlyDto>> =
        _cityWeatherState

    fun getMyLocationWeather(
        latitude: Double,
        longitude: Double,
        apiKey: String
    ) {
        viewModelScope.launch {
            getMyLocationWeatherUseCase(latitude, longitude, apiKey).collect { state ->
                when (state) {
                    is StateWrapper.Loading -> _myLocationWeatherState.value = StateWrapper.Loading
                    is StateWrapper.Success -> {
                        _myLocationWeatherState.value = StateWrapper.Success(state.data)
                    }
                    is StateWrapper.Failure -> {
                        _myLocationWeatherState.value = StateWrapper.Failure(
                            state.isNetworkError,
                            state.errorCode,
                            state.errorMessage
                        )
                    }
                }
            }
        }
    }

    fun getWeatherByCity(city: String, key: String) {
        viewModelScope.launch {
            getWeatherByCityUseCase(city, key).collect { state ->
                when (state) {
                    is StateWrapper.Loading -> _cityWeatherState.setValue(StateWrapper.Loading)
                    is StateWrapper.Success -> {
                        _cityWeatherState.setValue(StateWrapper.Success(state.data))
                    }
                    is StateWrapper.Failure -> {
                        _cityWeatherState.setValue(
                            StateWrapper.Failure(
                                state.isNetworkError,
                                state.errorCode,
                                state.errorMessage
                            )
                        )
                    }
                }
            }
        }
    }

    @ExperimentalPagingApi
    fun getLocalWeather() {
        viewModelScope.launch {
            getLocalWeatherUseCase().onStart {
                _localWeatherState.setValue(StateWrapper.Loading)
            }.catch {
                _localWeatherState.setValue(
                    StateWrapper.Failure(
                        false,
                        null,
                        "Error getting local weather from DB"
                    )
                )
            }.collect {
                _localWeatherState.setValue(StateWrapper.Success(it))
            }
        }
    }

}