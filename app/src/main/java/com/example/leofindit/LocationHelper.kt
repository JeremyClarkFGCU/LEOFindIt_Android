package com.example.leofindit

import android.content.Context
import android.location.LocationManager

object LocationHelper {
    private var locationManager : LocationManager? = null

    fun locationInit(context : Context) {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
}