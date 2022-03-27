package com.ezzy.weatherapptest.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.ezzy.weatherapptest.common.getOrAwaitValue
import com.ezzy.weatherapptest.data.local.WeatherDatabase
import com.ezzy.weatherapptest.util.testWeatherData
import com.google.common.truth.Truth.assertThat
import androidx.test.filters.SmallTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class WeatherTestDao {

    private lateinit var database: WeatherDatabase
    private lateinit var dao: WeatherDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        dao = database.weatherDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertWeather() = runBlockingTest {
        val testData = testWeatherData
        dao.insertMultiple(testData)
        val allWeatherData = dao.getAllWeather().getOrAwaitValue()
        assertThat(allWeatherData.size).isEqualTo(testData.size)
    }

    @Test
    fun testDeleteAllWeatherActuallyEmptiesTheDatabase() = runBlockingTest {
        val weatherData = testWeatherData
        dao.insertMultiple(weatherData)
        dao.clearWeather()
        val allWeather = dao.getAllWeather().getOrAwaitValue()
        assertThat(allWeather.size).isEqualTo(0)
    }


}