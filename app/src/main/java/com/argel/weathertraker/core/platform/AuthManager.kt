package com.argel.weathertraker.core.platform

import android.content.Context
import javax.inject.Inject
import androidx.core.content.edit


class AuthManager @Inject constructor(val context: Context) {

    private val authPreferences = "weatherTacker_Preferences"
    private val userKey = "key_user"
    private val tokenKey = "key_token"
    private val idTokenKey = "key_id_token"
    private val refreshTokenKey = "key_refresh_token"

    private val prefs = context.getSharedPreferences(authPreferences, Context.MODE_PRIVATE)

    var idToken: String? get() {
        val storedToken = prefs.getString(idTokenKey, null)
        return storedToken
        }
        set(value) {
            prefs.edit { putString(idTokenKey, value) }
        }
    var token: String? get() {
        val storedToken = prefs.getString(tokenKey, null)
        return storedToken
    }
        set(value) {
            prefs.edit { putString(tokenKey, value) }
        }

    var refreshToken: String? get() = prefs.getString(refreshTokenKey, null)
        set(value) = prefs.edit { putString(refreshTokenKey, value) }


    fun clear() {
        prefs.edit { clear() }
    }

}