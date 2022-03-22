package com.ezzy.weatherapptest.presentation.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezzy.weatherapptest.data.common.PermissionManager
import com.ezzy.weatherapptest.data.remote.mappers.WeatherMapper
import com.ezzy.weatherapptest.data.resource.StateWrapper
import com.ezzy.weatherapptest.databinding.FragmentWeatherBinding
import com.ezzy.weatherapptest.presentation.ui.fragments.weather_main.WeatherViewModel
import com.ezzy.weatherapptest.utils.gone
import com.ezzy.weatherapptest.utils.showToast
import com.ezzy.weatherapptest.utils.visible
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalPagingApi
@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val weatherViewModel: WeatherViewModel by viewModels()
    val permissionManager = PermissionManager.from(this)

    private val weatherAdapter: NewWeatherAdapter by lazy {
        NewWeatherAdapter()
    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient = FusedLocationProviderClient(requireContext())

        weatherViewModel.getLocalWeather()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()

        weatherAdapter.setOnClickListener {
            findNavController().navigate(
                WeatherFragmentDirections
                    .actionWeatherFragmentToWeatherDetailsFragment()
            )
        }
    }


    private fun initListeners() {
        weatherViewModel.localWeatherState.observe(viewLifecycleOwner) { state ->
            with(binding) {
                when (state) {
                    is StateWrapper.Loading -> {
                        layoutNoData.gone()
                        spinKit.visible()
                    }
                    is StateWrapper.Success -> {
                        spinKit.gone()
                        weatherAdapter.submitData(lifecycle, state.data)
                    }
                    is StateWrapper.Failure -> {
                        spinKit.gone()
                        showToast(state.errorMessage)
                    }
                }
            }
        }

        weatherViewModel.myLocationWeatherState.observe(viewLifecycleOwner) { state->
            with(binding) {
                when (state) {
                    is StateWrapper.Loading -> {
                       Timber.d("Loading")
                    }
                    is StateWrapper.Success -> {

                        val weather = WeatherMapper.toDomain(state.data.data[0])
                        tempCurrent.text = weather.temp.toString()
                        city.text = weather.city_name
                        time.text = weather.datetime

                    }
                    is StateWrapper.Failure -> {
                        showToast(state.errorMessage)
                    }
                }
            }
        }
    }

    private fun initUi() {
        setUpRecyclerView()
        fakeData()
    }

    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()

        weatherViewModel.getMyLocationWeather(
            -23.4353,
            0.234454,
            "07119db8fb0d4798ad928909be6c225a"
        )

//        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
//            val latitude = location.latitude
//            val longitude = location.longitude
//            weatherViewModel.getMyLocationWeather(
//                latitude,
//                longitude,
//                "07119db8fb0d4798ad928909be6c225a"
//            )
//        }

        lifecycleScope.launch {
            weatherAdapter.loadStateFlow.collectLatest { state ->
                with(binding) {
                    layoutNoData.isVisible = state.refresh is LoadState.Error
                    spinKit.isVisible =
                        state.refresh is LoadState.Loading && weatherAdapter.itemCount == 0

                    if (state.append.endOfPaginationReached) {
                        if (weatherAdapter.itemCount < 1) {

                            binding.layoutNoData.visible()
                        } else binding.layoutNoData.gone()
                    }
                }
            }
        }
    }

    private fun fakeData() {
        val fakeData = listOf(
            "Hello",
            "Hello",
            "Hello",
            "Hello",
            "Hello",
            "Hello",
            "Hello",
            "Hello",
            "Hello",
            "Hello",
            "Hello",
            "Hello",
            "Hello",
            "Hello",
            "Hello",
            "Hello",
            "Hello",
            "Hello",
            "Hello",
            "Hello",
            "Hello",
        )
//        weatherAdapter.submitList(fakeData)
    }

    private fun setUpRecyclerView() {
        binding.weatherRecylerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = weatherAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}