package com.ezzy.weatherapptest.data.resource

sealed class StateWrapper<out T> {
    data class Success<out T>(val data: T) : StateWrapper<T>()
    data class Failure<T>(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorMessage: String
    ) : StateWrapper<T>()
    object Loading : StateWrapper<Nothing>()
}