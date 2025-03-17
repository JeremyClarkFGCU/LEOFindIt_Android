// MainActivity.kt

package com.example.leofindit

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import com.example.leofindit.controller.DeviceController
import com.example.leofindit.controller.LEOPermissionHandler
import com.example.leofindit.model.BtleDevice
import com.example.leofindit.model.DeviceScanner

const val BLUETOOTH_PERMISSIONS_REQUEST_CODE = 101

class MainActivity : ComponentActivity() {

    private lateinit var deviceScanner: DeviceScanner
    private lateinit var permissionHandler: LEOPermissionHandler
    private lateinit var deviceController: DeviceController
    private val scannedDevices = mutableStateListOf<BtleDevice>()
    private var tag = "MainActivity"

    // Declare Activity Result Launcher
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        tag = "MainActivity.onCreate()"
        super.onCreate(savedInstanceState)
        deviceScanner = DeviceScanner(this)
        permissionHandler = LEOPermissionHandler()
        deviceController = DeviceController(deviceScanner, permissionHandler)

        // Initialize Activity Result Launcher
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                if (permissions.all { it.value }) {
                    Log.d(tag, "requestPermissionLauncher all permissions granted.")
                    // Call onPermissionsGranted here:
                    deviceController.onPermissionsGranted()
                } else {
                    deviceController.onPermissionsDenied()
                    showPermissionDeniedDialog()
                }
            }

        setContent {
            Column {
                Text("Hello LEOFindIt")
                deviceList(scannedDevices)
            }
        }
        deviceScanner.setScanCallback(object : DeviceScanner.ScanCallback {
            override fun onScanResult(devices: List<BtleDevice>) {
                scannedDevices.clear()
                scannedDevices.addAll(devices)
                println("Number of scanned devices: ${scannedDevices.size}")
            }
        })

        // Start Bluetooth Scanning HERE
        Log.d(tag,"Calling deviceController.startScanning() passing the launcher")
        deviceController.startScanning(this, requestPermissionLauncher)
        tag = "MainActivity"
    }

    override fun onDestroy() {
        super.onDestroy()
        deviceScanner.stopScanning()
    }

    @Composable
    fun deviceList(devices: List<BtleDevice>) {
        Text("Device count: ${devices.size}") // Add this line
        LazyColumn {
            items(devices) { device ->
                Text(text = device.deviceName)
            }
        }
    }

    /** This calls a Dialog box that double-checks that the user wants to deny bluetooth permissions.
     *
     *
     * @return Nothing
     *
     *
     */
    private fun showPermissionDeniedDialog() {
        tag = "MainActivity.showPermissionDeniedDialog()"
        AlertDialog.Builder(this)
            .setTitle("Permission Required")
            .setMessage("Bluetooth and location permissions are essential for this app to function. Please grant the permissions in app settings.")
            .setPositiveButton("Go to Settings") { _, _ ->
                // Open app settings
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setCancelable(false) // Prevent user from dismissing the dialog by tapping outside
            .setNegativeButton("I don't want to use this app.") { _, _ ->
                // Log the event when the user declines again.
                Log.d(tag, "User refused to grant permissions.")
                Toast.makeText(this@MainActivity, "App will now close. Please restart and grant permissions if you wish to use the app.", Toast.LENGTH_LONG).show()
                // Optionally close the app after a delay here as well
                Handler(Looper.getMainLooper()).postDelayed({
                    finish()
                }, 2000)
            }
            .show()
    }

    override fun onResume() {
        super.onResume()
        // Check permissions again when the activity resumes
        // This is crucial to handle the case where the user grants permissions in settings
        if (permissionHandler.checkRequiredPermissions(this)) {
            deviceController.onPermissionsGranted()
        }
    }

}