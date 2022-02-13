package com.example.weatherforecast.data.module

import android.content.Context
import com.example.weatherforecast.data.db.ForecastDatabase
import com.example.weatherforecast.data.current.db.CurrentWeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideForecast(@ApplicationContext context: Context): ForecastDatabase {
        return ForecastDatabase(context)
    }

    @Provides
    fun provideCurrentWeatherDao(forecastDatabase: ForecastDatabase): CurrentWeatherDao {
        return forecastDatabase.currentWeatherDao()
    }
}