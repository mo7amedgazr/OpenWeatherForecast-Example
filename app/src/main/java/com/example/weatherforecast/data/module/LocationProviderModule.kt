package com.example.weatherforecast.data.module

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocationProviderModule {

    @Provides
    fun provideSharedPref(@ApplicationContext context: Context) : FusedLocationProviderClient {
        return FusedLocationProviderClient(context)
    }
}