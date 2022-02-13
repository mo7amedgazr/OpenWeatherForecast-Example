package com.example.weatherforecast.data.current

import com.example.common.data.module.NetworkModule
import com.example.common.data.preferences.SharedPrefs
import com.example.weatherforecast.data.current.db.CurrentWeatherDao
import com.example.weatherforecast.data.current.remote.api.CurrentWeatherApi
import com.example.weatherforecast.data.current.repository.CurrentWeatherRepositoryImpl
import com.example.weatherforecast.domain.current.CurrentWeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class CurrentWeatherModule {

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(CurrentWeatherApi::class.java)

    @Singleton
    @Provides
    fun provideCurrentWeatherRepository(
        currentWeatherApi: CurrentWeatherApi,
        sharedPrefs: SharedPrefs,
        weatherDao: CurrentWeatherDao
    ): CurrentWeatherRepository {
        return CurrentWeatherRepositoryImpl(currentWeatherApi, sharedPrefs, weatherDao)
    }
}