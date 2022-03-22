package com.ezzy.weatherapptest.domain.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "weather")
data class Weather (
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val isFavorite: Boolean? = false,
    val app_temp: Double,
    val aqi: Int,
    val city_name: String,
    val country_code: String,
    val datetime: String,
    val lat: Double,
    val lon: Double,
    val ob_time: String,
    val pod: String,
    val precip: Int,
    val pres: Int,
    val rh: Int,
    val sunrise: String,
    val sunset: String,
    val temp: Double,
    val timezone: String,
    val ts: Int,
    val wind_cdir: String,
    val wind_cdir_full: String,
    val wind_dir: Int,
    val wind_spd: Double,
    val description: String,
    val icon: String
): Parcelable