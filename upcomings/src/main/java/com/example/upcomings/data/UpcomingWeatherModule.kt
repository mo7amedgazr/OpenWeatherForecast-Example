package com.example.upcomings.data

import com.example.common.data.module.NetworkModule
import com.example.common.data.preferences.SharedPrefs
import com.example.upcomings.data.remote.UpcomingWeatherApi
import com.example.upcomings.data.repository.UpcomingWeatherRepositoryImpl
import com.example.upcomings.domain.UpcomingWeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class UpcomingWeatherModule {

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(UpcomingWeatherApi::class.java)

    @Singleton
    @Provides
    fun provideCurrentWeatherRepository(
        upcomingWeatherApi: UpcomingWeatherApi,
        sharedPrefs: SharedPrefs
    ): UpcomingWeatherRepository {
        return UpcomingWeatherRepositoryImpl(upcomingWeatherApi, sharedPrefs)
    }
}