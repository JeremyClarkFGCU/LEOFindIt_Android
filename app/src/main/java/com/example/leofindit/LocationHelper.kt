package com.example.leofindit

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState

object LocationHelper {
    private var locationManager : LocationManager? = null

    private val locationPermissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            // Manifest.permission.ACCESS_BACKGROUND_LOCATION
    )
    @OptIn(ExperimentalPermissionsApi::class)
    val LocalLocationPermissionState = compositionLocalOf <MultiplePermissionsState> {
        error("No location permission state provided")
    }
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun locationCheck() : Boolean {
        val permissionsStatus = rememberMultiplePermissionsState(
            permissions = locationPermissions
        )
        return permissionsStatus.allPermissionsGranted
    }
    @SuppressLint("PermissionLaunchedDuringComposition")
    @Composable
    @OptIn(ExperimentalPermissionsApi::class)
    fun RequestPermission(permissions : MultiplePermissionsState) {

        if (!permissions.allPermissionsGranted) {
            permissions.launchMultiplePermissionRequest()
        }
    }

    fun locationInit(context : Context) {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
}