package com.argel.weathertraker.core.extension

import android.widget.ImageView
import coil.load


fun ImageView.loadFromURL(
    url: String?
) = url?.let {
    this.load(url) {
        crossfade(true)
    }
}