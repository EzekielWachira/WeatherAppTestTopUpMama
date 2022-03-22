package com.ezzy.weatherapptest.data.remote.dto.current_weather

data class WeatherData(
    val app_temp: Double,
    val aqi: Int,
    val city_name: String,
    val clouds: Int,
    val country_code: String,
    val datetime: String,
    val dewpt: Double,
    val dhi: Float,
    val dni: Float,
    val elev_angle: Double,
    val ghi: Int,
    val h_angle: Int,
    val lat: Double,
    val lon: Double,
    val ob_time: String,
    val pod: String,
    val precip: Int,
    val pres: Int,
    val rh: Int,
    val slp: Double,
    val snow: Int,
    val solar_rad: Int,
    val state_code: String,
    val station: String,
    val sunrise: String,
    val sunset: String,
    val temp: Double,
    val timezone: String,
    val ts: Int,
    val uv: Float,
    val vis: Int,
    val weather: Weather,
    val wind_cdir: String,
    val wind_cdir_full: String,
    val wind_dir: Int,
    val wind_spd: Double
)