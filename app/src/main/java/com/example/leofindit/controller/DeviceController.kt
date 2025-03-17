package com.example.leofindit.controller

import android.app.Activity
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.example.leofindit.BLUETOOTH_PERMISSIONS_REQUEST_CODE
import com.example.leofindit.model.DeviceScanner

class DeviceController(
    private val deviceScanner: DeviceScanner,
    private val permissionHandler: LEOPermissionHandler
) : LEOPermissionHandler.PermissionCallback {

    private var isScanning = false
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
    // DeviceController.kt
    fun startScanning(
        context: Activity,
        requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    ) {
        val requestCode = BLUETOOTH_PERMISSIONS_REQUEST_CODE
        tag = "DeviceController.startScanning()"
        Log.d(tag, "DeviceController.startScanning() called with activity: $context")
        if (isScanning) return
        Log.d(tag, "isScanning false, checking BT permissions with permissionHandler")
        if (permissionHandler.checkRequiredPermissions(context)) {
            Log.i(tag, "Required permissions already granted or empty. Starting scan.")
            deviceScanner.startScanning()
            isScanning = true
        } else {
            Log.w(
                tag,
                "Required permissions not granted. Requesting Permissions using permissionHandler.requestRequiredPermissions"
            )
            //changed line
            permissionHandler.requestRequiredPermissions(requestPermissionLauncher)

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
    }
}