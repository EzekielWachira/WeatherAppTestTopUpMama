package com.ezzy.weatherapptest.presentation.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ezzy.weatherapptest.databinding.WeatherItemBinding
import com.ezzy.weatherapptest.domain.domain.Weather
import com.ezzy.weatherapptest.utils.Constants.ICON_APPEND_URL
import com.ezzy.weatherapptest.utils.loadImage

class NewWeatherAdapter: PagingDataAdapter<Weather, NewWeatherAdapter.WeatherViewHolder>(COMPARATOR) {

    private var onItemClickListener: ((Weather?) -> Unit)? = null

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.apply {
            getItem(position)?.let { bindItem(it) }
            baseLayout.setOnClickListener {
                onItemClickListener?.let {
                    it(getItem(position))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(
            WeatherItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Weather>() {
            override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean =
                oldItem == newItem
        }
    }

    fun setOnClickListener(listener: (Weather?) -> Unit) {
        onItemClickListener = listener
    }

    inner class WeatherViewHolder(
        private val binding: WeatherItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        val baseLayout = binding.baseLayout

        fun bindItem(item: Weather) {
            with(binding) {
                cityName.text = item.city_name
                favorite.isVisible = item.isFavorite!!
                condition.text = item.description
                time.text = item.datetime
                cityTemp.text = "${item.temp}° C"
                weatherIcon.loadImage("$ICON_APPEND_URL${item.icon}.png")
            }

        }

    }
}