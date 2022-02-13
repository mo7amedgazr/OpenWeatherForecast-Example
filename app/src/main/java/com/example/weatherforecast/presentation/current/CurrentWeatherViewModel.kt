package com.example.weatherforecast.presentation.current

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.data.utils.DataState
import com.example.weatherforecast.domain.current.entity.CurrentWeatherEntity
import com.example.weatherforecast.domain.current.usecase.CurrentWeatherUseCase
import com.example.common.data.views.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val currentWeatherUseCase: CurrentWeatherUseCase
) : BaseViewModel() {

    private val _currentWeatherLiveData = MutableLiveData<CurrentWeatherEntity>()
    val currentWeatherLiveData: LiveData<CurrentWeatherEntity> get() = _currentWeatherLiveData

    // ignore cache parameter added for handling user switch unit settings.
    fun getCurrentWeather(
        latitude: Double, longitude: Double, ignoreCache: Boolean = false
    ) = viewModelScope.launch {
        updateLoader(true)
        currentWeatherUseCase.execute(
            CurrentWeatherUseCase.Params(
                latitude,
                longitude,
                ignoreCache
            )
        ).collect { response ->
            updateLoader(false)
            when (response) {
                is DataState.GenericError -> {
                    response.error?.message?.let { showError.value = it }
                }
                is DataState.Success -> {
                    response.value?.let { result ->
                        _currentWeatherLiveData.value = result
                    }
                }
            }
        }
    }

}