package com.argel.weathertraker.core.platform

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.telephony.TelephonyManager
import android.util.Log
import java.lang.reflect.Method
import javax.inject.Inject
import kotlin.jvm.javaClass

/**
 * Created by Didier Chan on 06/11/2024.
 * didier.chan@dacodes.com.mx
 */

class NetworkHandler @Inject constructor(private val context: Context) {

    val isNetworkConnected get() = isConnected()

    private fun isMobileDataEnabled(context: Context): Boolean {
        return try {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val method: Method = telephonyManager.javaClass.getDeclaredMethod("getDataEnabled")
            method.invoke(telephonyManager) as Boolean
        } catch (e: Exception) {
            Log.e("NetworkUtil", "Error checking mobile data status", e)
            false
        }
    }

    private fun isWifiEnabled(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

        return networkCapabilities != null && networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }

    fun isMobileDataOrWifiEnabled(): Boolean {
        return isMobileDataEnabled(context) || isWifiEnabled(context)
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }
}

class NetworkConnection @Inject constructor(
    private val networkHandler: NetworkHandler
){
    fun isConnected(): Boolean {
        return when(networkHandler.isNetworkConnected){
            true -> { internetIsConnected() }
            else -> {
                false
            }
        }
    }

    fun internetIsConnected(): Boolean {
        return try {
            val command = "ping -c 1 google.com"
            Runtime.getRuntime().exec(command).waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }
}