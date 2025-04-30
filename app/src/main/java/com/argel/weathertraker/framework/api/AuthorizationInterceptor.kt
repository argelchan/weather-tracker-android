package com.argel.weathertraker.framework.api

import com.argel.weathertraker.core.platform.AuthManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationInterceptor @Inject constructor(private val authManager: AuthManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!authManager.idToken.isNullOrEmpty()) {
            println("Bearer ${authManager.idToken}")

            val original = chain.request()
            val builder = original.newBuilder()
                .method(original.method, original.body)
            builder.header("Authorization", "Bearer ${authManager.idToken}")
            chain.proceed(builder.build())
        } else {
            chain.proceed(chain.request())
        }
    }
}