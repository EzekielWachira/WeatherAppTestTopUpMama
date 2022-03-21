package com.ezzy.weatherapptest.di

import com.ezzy.weatherapptest.data.remote.WeatherApi
import com.ezzy.weatherapptest.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import zerobranch.androidremotedebugger.logging.NetLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(
        okHttpClient: OkHttpClient
    ): WeatherApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(WeatherApi::class.java)

    @Provides
    @Singleton
    fun provideNetLoggingInterceptor(): NetLoggingInterceptor = NetLoggingInterceptor()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        netLoggingInterceptor: NetLoggingInterceptor,
    ) = OkHttpClient.Builder()
        .addInterceptor(netLoggingInterceptor)
        .build()
}