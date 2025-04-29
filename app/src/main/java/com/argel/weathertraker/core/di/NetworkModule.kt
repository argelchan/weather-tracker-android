package com.argel.weathertraker.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.argel.weathertraker.core.platform.AuthManager
import com.argel.weathertraker.core.platform.NetworkHandler
import com.argel.weathertraker.framework.api.ApiProvider
import com.argel.weathertraker.framework.api.AuthorizationInterceptor
import javax.inject.Singleton

/**
 * Created by Argel Chan on 06/11/2024.
 * didier.chan@ingenierosmafur.com
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideInterceptors(authManager: AuthManager) = AuthorizationInterceptor(authManager)

    @Provides
    @Singleton
    fun provideApiProvider(
        authorizationInterceptor: AuthorizationInterceptor
    ) = ApiProvider(authorizationInterceptor)

    @Provides
    @Singleton
    fun provideNetworkHandler(@ApplicationContext context: Context) = NetworkHandler(context)
}