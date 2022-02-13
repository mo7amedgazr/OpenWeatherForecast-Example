package com.example.weatherforecast.domain.current.usecase

import com.example.common.data.utils.DataState
import com.example.common.data.utils.ErrorResponse
import com.example.weatherforecast.BaseTest
import com.example.weatherforecast.domain.current.CurrentWeatherRepository
import com.example.weatherforecast.domain.current.entity.CurrentWeatherEntity
import junit.framework.Assert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito


class CurrentWeatherUseCaseTest : BaseTest(){

    private val repository = Mockito.mock(CurrentWeatherRepository::class.java)
    private val userCase = CurrentWeatherUseCase(repository)

    @Test
    fun `current weather successful`() = runBlocking {

        val response = MutableStateFlow(DataState.Success(CurrentWeatherEntity()))

        // When
        Mockito.`when`(repository.getCurrentWeather(12.112,3.3232,false)).thenReturn(response)

        // Then
        val result = userCase.execute(CurrentWeatherUseCase.Params(12.112,3.3232,false)).first()

        Assert.assertSame(response.value, result)


    }
    @Test
    fun `current weather  failure`() = runBlocking {

        val response =  MutableStateFlow(DataState.GenericError(400, ErrorResponse()))

        // When
        Mockito.`when`(repository.getCurrentWeather(12.112,3.3232,false)).thenReturn(response)

        // Then
        val result = userCase.execute(CurrentWeatherUseCase.Params(12.112,3.3232,false)).first()
        Assert.assertSame(response.value, result)

    }

}