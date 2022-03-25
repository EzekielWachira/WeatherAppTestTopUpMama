package com.ezzy.weatherapptest.data.remote.dto.current_weather_hourly

data class Data(
    val app_temp: Double,
    val aqi: Float,
    val city_name: String,
    val clouds: Float,
    val country_code: String,
    val datetime: String,
    val dewpt: Double,
    val dhi: Float,
    val dni: Float,
    val elev_angle: Double,
    val ghi: Float,
    val h_angle: Float,
    val lat: Double,
    val lon: Double,
    val ob_time: String,
    val pod: String,
    val precip: Float,
    val pres: Double,
    val rh: Float,
    val slp: Float,
    val snow: Float,
    val solar_rad: Float,
    val state_code: String,
    val station: String,
    val sunrise: String,
    val sunset: String,
    val temp: Float,
    val timezone: String,
    val ts: Float,
    val uv: Float,
    val vis: Float,
    val weather: Weather,
    val wind_cdir: String,
    val wind_cdir_full: String,
    val wind_dir: Float,
    val wind_spd: Double
)