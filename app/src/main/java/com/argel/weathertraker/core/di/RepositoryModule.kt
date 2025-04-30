package com.argel.weathertraker.core.di

import android.content.Context
import com.argel.weathertraker.core.data.source.PlaceRepositoryImpl
import com.argel.weathertraker.domain.repository.PlaceRepository
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

}