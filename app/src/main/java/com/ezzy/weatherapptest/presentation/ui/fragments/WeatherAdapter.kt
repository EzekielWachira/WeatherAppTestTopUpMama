package com.ezzy.weatherapptest.presentation.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ezzy.weatherapptest.data.remote.dto.current_weather_hourly.Minutely
import com.ezzy.weatherapptest.databinding.DailyWeatherItemBinding
import com.ezzy.weatherapptest.databinding.WeatherItemBinding

class WeatherAdapter: ListAdapter<Minutely, WeatherAdapter.WeatherViewHolder>(WeatherCallBack) {

    private var onItemClickListener: ((Minutely?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(
            DailyWeatherItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.apply {
            bindItem(getItem(position))
        }
    }

    private object WeatherCallBack : DiffUtil.ItemCallback<Minutely>() {
        override fun areItemsTheSame(oldItem: Minutely, newItem: Minutely): Boolean {
            return oldItem == oldItem
        }

        override fun areContentsTheSame(oldItem: Minutely, newItem: Minutely): Boolean {
            return oldItem == newItem
        }
    }

    fun setOnClickListener(listener: (Minutely?) -> Unit) {
        onItemClickListener = listener
    }

    inner class WeatherViewHolder(
        private val binding: DailyWeatherItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: Minutely) {
            with(binding) {
                time.text = item.timestamp_local
                temp.text = "${item.temp}° C"
            }
        }

    }
}