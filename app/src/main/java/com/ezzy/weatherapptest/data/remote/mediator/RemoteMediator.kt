package com.ezzy.weatherapptest.data.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ezzy.weatherapptest.data.local.WeatherDatabase
import com.ezzy.weatherapptest.data.local.dao.RemoteKeyDao
import com.ezzy.weatherapptest.data.local.dao.WeatherDao
import com.ezzy.weatherapptest.data.remote.WeatherApi
import com.ezzy.weatherapptest.data.remote.mappers.WeatherMapper
import com.ezzy.weatherapptest.domain.domain.Weather
import retrofit2.HttpException
import zerobranch.androidremotedebugger.AndroidRemoteDebugger
import java.io.IOException
import javax.inject.Inject


@ExperimentalPagingApi
class RemoteWeatherMediator @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDatabase: WeatherDatabase,
    private val weatherDao: WeatherDao,
    private val remoteKeyDao: RemoteKeyDao,
    private val searchCity: String = ""
) : RemoteMediator<Int, Weather>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Weather>
    ): MediatorResult {
//        val key = when (loadType) {
//            LoadType.REFRESH -> {
//                if (weatherDao.count() > 0) return MediatorResult.Success(false)
//                null
//            }
//            LoadType.PREPEND -> {
//                return MediatorResult.Success(endOfPaginationReached = true)
//            }
//            LoadType.APPEND -> {
//                getKey()
//            }
//        }

        return try {
            AndroidRemoteDebugger.Log.i("START")
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    AndroidRemoteDebugger.Log.i("REFRESH")
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    AndroidRemoteDebugger.Log.i("PREPEND")
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    AndroidRemoteDebugger.Log.i("APPEND")
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val cities = listOf(
                "Nairobi",
                "London",
                "New York",
                "Sydney",
                "Vienna",
                "Denver",
                "Bogota",
                "Oslo",
                "Helsinki",
                "Tokyo",
                "Berlin",
                "Rio",
                "Lisbon",
                "Buenos Aires",
                "Frankfurt",
                "Munich",
                "Dortmund",
                "Essen",
                "Leiden",
                "Melbourne"
            )

            val weatherList = mutableListOf<Weather>()

            if (searchCity.isNotEmpty()) {
                val response =
                    weatherApi.searchWeather(searchCity, "07119db8fb0d4798ad928909be6c225a")
                weatherList.add(WeatherMapper.toDomain(response.data[0]))
            } else {
                for (city in cities) {
                    val response =
                        weatherApi.searchWeather(city, "07119db8fb0d4798ad928909be6c225a")
                    weatherList.addAll(response.data.map { WeatherMapper.toDomain(it) })
                    AndroidRemoteDebugger.Log.i("$weatherList")
                }
            }
            val endOfPagnationReached = weatherList.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPagnationReached) null else currentPage + 1

            weatherDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    weatherDao.clearWeather()
                    remoteKeyDao.clearRemoteKeys()
                }

                val keys = weatherList.map { transactionData ->
                    RemoteKeys(
                        null,
                        prevPage = prevPage,
                        nextPage = nextPage,
                        endOfPagnationReached
                    )
                }

                remoteKeyDao.insertAll(keys)
                weatherDao.insertMultiple(weatherList)
            }
            MediatorResult.Success(endOfPaginationReached = false)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }


    private suspend fun getKey(): RemoteKeys? {
        return weatherDatabase.remoteKeysDao().getKeys().firstOrNull()
    }

    private fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Weather>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeyDao.remoteKeysRepoId(id)
            }
        }
    }

    private fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Weather>
    ): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { transaction ->
                remoteKeyDao.remoteKeysRepoId(transaction.id!!)
            }
    }

    private fun getRemoteKeyForLastItem(
        state: PagingState<Int, Weather>
    ): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { transaction ->
                remoteKeyDao.remoteKeysRepoId(transaction.id!!)
            }
    }
}