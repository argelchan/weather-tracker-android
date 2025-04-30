package com.argel.weathertraker.core.di

import android.content.Context
import com.argel.weathertraker.core.platform.NetworkHandler
import com.argel.weathertraker.data.api.OpenWeatherApi
import com.argel.weathertraker.data.source.PlaceRepositoryImpl
import com.argel.weathertraker.data.source.WeatherRepositoryImpl
import com.argel.weathertraker.domain.repository.PlaceRepository
import com.argel.weathertraker.domain.repository.WeatherRepository
import com.argel.weathertraker.framework.api.ApiProvider
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesPlacesClient(
        @ApplicationContext applicationContext: Context
    ): PlacesClient =
        Places.createClient(applicationContext)

    @Provides
    @Singleton
    fun providesPlaceRepository(
        placesClient: PlacesClient
    ): PlaceRepository =
        PlaceRepositoryImpl(placesClient)

    @Provides
    @Singleton
    fun provideSubjectsRepository(
        apiProvider: ApiProvider,
        networkHandler: NetworkHandler
    ): WeatherRepository =
        WeatherRepositoryImpl(
            networkHandler,
            apiProvider.getEndpoint(OpenWeatherApi::class.java)
        )
}