package com.example.leofindit.controller

import android.app.Activity
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.example.leofindit.model.DeviceScanner
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class DeviceController(
    private val deviceScanner: DeviceScanner,
    private val permissionHandler: LEOPermissionHandler
) : LEOPermissionHandler.PermissionCallback {

    internal var isScanning by mutableStateOf(false)
    private var tag: String = "DeviceController"

    init { // Must initialize the permissions handler
        permissionHandler.setPermissionCallback(this)
    }

    /**
     * This function determines if the app is already scanning.
     * If not then decides whether proper permissions are granted and requests them if
     * necessary.
     * @context This must be an Activity. called from MainActivity, so it should be correct.
     * @return No return type. This calls other methods
     */


    fun startScanning(
        context: Activity,
        requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    ) {
        tag = "DeviceController.startScanning()"
        Log.d(tag, "DeviceController.startScanning() called with activity: $context")

        if (isScanning) {
            Log.d(tag, "Already scanning, returning")
            return
        }

        Log.d(tag, "Checking BT permissions with permissionHandler")
        if (!permissionHandler.checkRequiredPermissions(context)) {
            Log.w(
                tag,
                "Required permissions not granted. Requesting Permissions using permissionHandler.requestRequiredPermissions"
            )
            permissionHandler.requestRequiredPermissions(requestPermissionLauncher)
        } else {
            Log.i(tag, "Permissions granted or already available. Starting scan.")
            deviceScanner.startScanning()
            isScanning = true
        }
    }

    override fun onPermissionsGranted() {
        tag = "DeviceController.onPermissionsGranted()"
        Log.i(tag, "Permissions Granted. Starting scan.")
        deviceScanner.startScanning()
        isScanning = true
        tag = "DeviceController"
    }

    override fun onPermissionsDenied() {
        tag = "DeviceController.onPermissionsDenied()"
        Log.e(tag, "Permissions Denied. Cannot start scan.")
        tag = "DeviceController"
    }// End of onPermissionsDenied Override fun

    /** This function
     * @param context - MainActivity
     * @param requestPermissionLauncher - instance of the PermissionsCallback in MainActivity.
     */


    fun toggleScanning(
        context: Activity,
        requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    ) {
        if (isScanning) {
            deviceScanner.stopScanning()
            isScanning = false
            Log.d(tag, "Scanning stopped")
        } else {
            startScanning(context, requestPermissionLauncher)
            Log.d(tag, "Scanning started")
        }
    }
}
