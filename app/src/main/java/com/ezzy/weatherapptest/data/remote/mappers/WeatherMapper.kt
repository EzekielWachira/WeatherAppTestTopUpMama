package com.ezzy.weatherapptest.data.remote.mappers

import com.ezzy.weatherapptest.data.remote.dto.current_weather.WeatherData
import com.ezzy.weatherapptest.domain.domain.Weather

object WeatherMapper : DtoToDomainMapper<WeatherData, Weather> {
    override fun toDomain(dto: WeatherData): Weather {
        return Weather(
            app_temp = dto.app_temp,
            aqi = dto.aqi,
            city_name = dto.city_name,
            country_code = dto.country_code,
            datetime = dto.datetime,
            lat = dto.lat,
            lon = dto.lon,
            ob_time = dto.ob_time,
            pod = dto.pod,
            precip = dto.precip,
            pres = dto.pres,
            rh = dto.rh,
            sunrise = dto.sunrise,
            sunset = dto.sunset,
            temp = dto.temp,
            timezone = dto.timezone,
            ts = dto.ts,
            wind_cdir = dto.wind_cdir,
            wind_cdir_full = dto.wind_cdir_full,
            wind_dir = dto.wind_dir,
            wind_spd = dto.wind_spd,
            description = dto.weather.description,
            icon = dto.weather.icon
        )
    }
}