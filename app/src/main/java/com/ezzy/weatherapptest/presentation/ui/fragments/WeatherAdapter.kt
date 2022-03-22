package com.ezzy.weatherapptest.presentation.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ezzy.weatherapptest.databinding.WeatherItemBinding

class WeatherAdapter: ListAdapter<String, WeatherAdapter.WeatherViewHolder>(WeatherCallBack) {

    private var onItemClickListener: ((String?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(
            WeatherItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.apply {
            bindItem(getItem(position))
            baseLayout.setOnClickListener {
                onItemClickListener?.let {
                    it(getItem(position))
                }
            }
        }
    }

    private object WeatherCallBack : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == oldItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    fun setOnClickListener(listener: (String?) -> Unit) {
        onItemClickListener = listener
    }

    inner class WeatherViewHolder(
        binding: WeatherItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        val baseLayout = binding.baseLayout

        fun bindItem(item: String) {

        }

    }
}