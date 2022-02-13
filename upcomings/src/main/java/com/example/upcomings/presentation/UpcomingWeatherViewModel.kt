package com.example.upcomings.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.data.utils.DataState
import com.example.common.data.views.BaseViewModel
import com.example.upcomings.domain.entity.WeatherListItem
import com.example.upcomings.domain.usecase.UpcomingWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingWeatherViewModel @Inject constructor(
    private val upcomingWeatherUseCase: UpcomingWeatherUseCase,
) : BaseViewModel() {

    private val _upcomingWeatherLiveData = MutableLiveData<List<WeatherListItem>>()
    val upcomingWeatherLiveData: LiveData<List<WeatherListItem>> get() = _upcomingWeatherLiveData

    // ignore cache parameter added for handling user switch unit settings.
    fun getUpcomingWeather() = viewModelScope.launch {
        updateLoader(true)
        upcomingWeatherUseCase.execute(
            UpcomingWeatherUseCase.Params()
        ).collect { response ->
            updateLoader(false)
            when (response) {
                is DataState.GenericError -> {
                    response.error?.message?.let { showError.value = it }
                }
                is DataState.Success -> {
                    response.value?.let { result ->
                        _upcomingWeatherLiveData.value = result
                    }
                }
            }
        }
    }

}