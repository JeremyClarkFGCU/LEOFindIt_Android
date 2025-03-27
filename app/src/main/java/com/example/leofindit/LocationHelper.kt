package com.example.leofindit

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState


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

    @SuppressLint("PermissionLaunchedDuringComposition")
    @OptIn(ExperimentalPermissionsApi::class)
    fun requestPermission(permissions: MultiplePermissionsState, context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            val uri = Uri.fromParts("package",context.packageName, null )
            data = uri
        }
        if (!permissions.shouldShowRationale) {
            permissions.launchMultiplePermissionRequest()
        }
        else {
            Toast.makeText(context, "Permission denied, please go into settings to enable " +
                    "permissions", Toast.LENGTH_LONG).show()
            context.startActivity(intent)
        }
    }
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun checkingLocationPermissionState(permissionsState: MultiplePermissionsState): State<Boolean> {
        val hasPermissions = remember { mutableStateOf(permissionsState.allPermissionsGranted) }

        LaunchedEffect(permissionsState.revokedPermissions) {
            hasPermissions.value = permissionsState.allPermissionsGranted
        }

        return hasPermissions
    }
    @Composable
    fun checkingLocationEnabledState() : State<Boolean> {
        val context = LocalContext.current
        return produceState(initialValue = isLocationServiceEnabled()) {

            val checkLocation = {
                value = isLocationServiceEnabled()
            }

            val receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    checkLocation()
                }
            }

            context.registerReceiver(receiver, IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION))
            checkLocation()

            awaitDispose {
                context.unregisterReceiver(receiver)
            }
        }
    }

    //function returns true if Both Location Service and Permissions are granted
    @Composable
    @OptIn(ExperimentalPermissionsApi::class)
    fun scanPreCheck(permissionsState: MultiplePermissionsState) : Boolean {
        val isLocationEnabled by checkingLocationEnabledState()
        val hasPermissions by checkingLocationPermissionState(permissionsState)
        return isLocationEnabled && hasPermissions
    }

    fun isLocationServiceEnabled () : Boolean {
        return locationManager?.isLocationEnabled == true
    }

    fun enableLocationService(context: Context) {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        context.startActivity(intent)
    }

    fun locationInit(context : Context) {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
}
