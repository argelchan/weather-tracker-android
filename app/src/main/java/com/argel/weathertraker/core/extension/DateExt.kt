package com.argel.weathertraker.core.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun String.formatDate(
    desiredFormat: String,
    currentFormat: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
    locale: Locale = Locale.getDefault()
): String? {
    return try {
        val parser = SimpleDateFormat(currentFormat, locale)
        val formatter = SimpleDateFormat(desiredFormat, locale)
        val parsedDate = parser.parse(this)
        parsedDate?.let { formatter.format(it) }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Long.toFormattedTime(): String {
    val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(Date(this * 1000))
}