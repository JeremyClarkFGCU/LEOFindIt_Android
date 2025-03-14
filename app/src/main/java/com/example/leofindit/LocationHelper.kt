package com.example.leofindit

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import androidx.compose.runtime.State


object LocationHelper {
    private var locationManager: LocationManager? = null

    private val locationPermissions = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        // Manifest.permission.ACCESS_BACKGROUND_LOCATION
    )

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun rememberLocationPermissionState(): MultiplePermissionsState {
        return rememberMultiplePermissionsState(permissions = locationPermissions)
    }

    @Composable
    fun checkingLocationEnabledState() : State<Boolean> {
        val context = LocalContext.current
        return produceState(initialValue = isLocationServiceEnabled()) {

            val checkLocationStatus = {
                value = isLocationServiceEnabled()
            }

            val receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    checkLocationStatus()
                }
            }

            context.registerReceiver(receiver, IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION))
            checkLocationStatus()

            awaitDispose {
                context.unregisterReceiver(receiver)
            }
        }
    }
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun locationCheck(permissionState: MultiplePermissionsState): Boolean {
        return permissionState.allPermissionsGranted && isLocationServiceEnabled()
    }

    @SuppressLint("PermissionLaunchedDuringComposition")
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun RequestPermission(permissions: MultiplePermissionsState) {
        if (!permissions.allPermissionsGranted) {
            permissions.launchMultiplePermissionRequest()
        }
    }


    fun isLocationServiceEnabled () : Boolean {
        return locationManager?.isLocationEnabled == true
    }

    fun enableLocationService(context: Context) {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        context.startActivity(intent)
    }

    @OptIn(ExperimentalPermissionsApi::class)
    fun locationInit(context : Context) {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
}
