package com.example.upcomings.domain.usecase

import com.example.common.data.utils.DataState
import com.example.common.data.utils.ErrorResponse
import com.example.upcomings.BaseTest
import com.example.upcomings.domain.UpcomingWeatherRepository
import com.example.upcomings.domain.entity.WeatherListItem
import junit.framework.Assert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito

class UpcomingWeatherUseCaseTest : BaseTest(){
    private val repository = Mockito.mock(UpcomingWeatherRepository::class.java)
    private val userCase = UpcomingWeatherUseCase(repository)

    @Test
    fun `weather list successful`() = runBlocking {

        val response = MutableStateFlow(DataState.Success(listOf<WeatherListItem>()))

        // When
        Mockito.`when`(repository.getUpcomingWeather()).thenReturn(response)

        // Then
        val result = userCase.execute(UpcomingWeatherUseCase.Params()).first()

        Assert.assertSame(response.value, result)


    }
    @Test
    fun `weather list failure`() = runBlocking {
        val response =  MutableStateFlow(DataState.GenericError(400, ErrorResponse()))

        // When
        Mockito.`when`(repository.getUpcomingWeather()).thenReturn(response)

        // Then
        val result = userCase.execute(UpcomingWeatherUseCase.Params()).first()
        Assert.assertSame(response.value, result)
    }


}