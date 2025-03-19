package com.example.leofindit.controller

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import com.example.leofindit.BLUETOOTH_PERMISSIONS_REQUEST_CODE

// Class to specifically handle checking and requesting required permissions.
class LeoPermissionHandler {
    // for Logging Purposes
    private var tag = "LEOPermissionHandler"
    private val requestCode = BLUETOOTH_PERMISSIONS_REQUEST_CODE

    // List of required permissions
    private val leoPermissionsList: List<String> = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.BLUETOOTH_SCAN
    )
    var missingPermissionsArray: ArrayList<String> = ArrayList()

    // Permission Callback interface allows View to interact with Permissions
    interface PermissionCallback {
        fun onPermissionsGranted()
        fun onPermissionsDenied()
    }

    private var permissionCallback: PermissionCallback? = null

    fun setPermissionCallback(callback: PermissionCallback) {
        permissionCallback = callback
    }

    /**
     * This function checks for any required permissions to start BT Scanner
     *
     * @param context  is required for ContextCompat to check BT Permissions
     * @return nothing
     * True for empty missingPermissionsArray (i.e. All Permissions Granted)
     * False for missingPermissionsArray not empty (i.e. Some permissions not granted)
     */
    fun checkRequiredPermissions(context: Context): Boolean {
        tag = "LEOPermissionHandler.checkRequiredPermissions()"
        Log.d(tag, "checkRequiredPermissions() called")
        missingPermissionsArray.clear()
        for (permission in leoPermissionsList) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                Log.d(tag, "$permission not granted. Adding to missingPermissionsArray")
                missingPermissionsArray.add(permission)
            }
        }
        if (missingPermissionsArray.isEmpty()) {
            Log.i(tag, "All required permissions are already granted.")
            return true
        }
        Log.i(tag, "Missing permissions: ${missingPermissionsArray.joinToString()}")
        return false
    }

    /**
     * This function requests any missing required permissions to start BT Scanner
     *
     * @activity is a sub-context required for ActivityCompat to request BT Permissions
     * Does not return.
     */
    fun requestRequiredPermissions(requestPermissionLauncher: ActivityResultLauncher<Array<String>>) {
        tag = "LEOPermissionHandler.requestRequiredPermissions()"
        Log.d(tag, "Called permissionHandler.requestRequiredPermissions()")
        if (missingPermissionsArray.isNotEmpty()) {
            requestPermissionLauncher.launch(missingPermissionsArray.toTypedArray())
            Log.i(tag, "missingPermissionsArray contained values.")
        } else {
            Log.i(tag, "missingPermissionsArray was empty. No permissions requested.")
        }
    }
}