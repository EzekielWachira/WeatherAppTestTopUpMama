package com.ezzy.weatherapptest.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezzy.weatherapptest.R
import com.ezzy.weatherapptest.data.resource.StateWrapper
import com.ezzy.weatherapptest.databinding.FragmentWeatherDetailsBinding
import com.ezzy.weatherapptest.domain.domain.Weather
import com.ezzy.weatherapptest.presentation.ui.fragments.weather_main.WeatherViewModel
import com.ezzy.weatherapptest.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class WeatherDetailsFragment : Fragment() {

    private var _binding: FragmentWeatherDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: WeatherDetailsFragmentArgs by navArgs()
    private var weather: Weather? = null

    private val weatherViewModel: WeatherViewModel by viewModels()
    private val weatherAdapter: WeatherAdapter by lazy {
        WeatherAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDetailsBinding.inflate(inflater, container, false)

        weather = args.weather.also {
            with(binding) {
                city.text = it.city_name
                tempCurrent.text = "${it.temp}° C"
                time.text = it.datetime
                imageMain.setImageResource(when(it.pod) {
                    "p" -> R.drawable.ic_sun_simple_2
                    "n" -> R.drawable.ic_monsters_special_night
                    else -> R.drawable.sunset
                })
            }
            weatherViewModel.getWeatherByCity(it.city_name, "5ef7d7ca2c6342fda9581f27eae41c3b")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        setUpUi()

        initUiState()
    }

    private fun setUpUi() {
        with(binding) {
            fabFavorite.setOnClickListener {
                weather!!.isFavorite = !weather!!.isFavorite!!
                weatherViewModel.markWeatherAsFavorite(weather!!)
                showToast(
                    when {
                        weather!!.isFavorite!! -> "Weather marked as favorite"
                        !weather!!.isFavorite!! -> "Weather removed as favorite"
                        else -> "Error"
                    }
                )
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.minutelyRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = weatherAdapter
        }
    }

    private fun initUiState() {
        weatherViewModel.cityWeatherState.observe(viewLifecycleOwner) { state->
            when(state) {
                is StateWrapper.Loading -> Timber.d("Loading")
                is StateWrapper.Success -> weatherAdapter.submitList(state.data.minutely)
                is StateWrapper.Failure -> Timber.e(state.errorMessage)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}