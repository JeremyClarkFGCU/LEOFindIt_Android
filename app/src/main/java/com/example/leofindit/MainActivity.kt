// MainActivity.kt

package com.example.leofindit

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.leofindit.controller.DeviceController
import com.example.leofindit.controller.LEOPermissionHandler
import com.example.leofindit.model.BtleDevice
import com.example.leofindit.model.DeviceScanner
import com.example.leofindit.ui.theme.*
import com.example.leofindit.view.scanButton
import com.example.leofindit.view.deviceListView
import com.example.leofindit.view.appTopBar
import android.util.Log
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateListOf


const val BLUETOOTH_PERMISSIONS_REQUEST_CODE = 101

class MainActivity : ComponentActivity() {

    internal lateinit var deviceScanner: DeviceScanner
    private lateinit var permissionHandler: LEOPermissionHandler
    private lateinit var deviceController: DeviceController
    private val scannedDevices = mutableStateListOf<BtleDevice>()
    private var tag = "MainActivity"

    // Declare Activity Result Launcher
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        tag = "MainActivity.onCreate()"
        val splashScreen = installSplashScreen()
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
            Leo_findit_aosTheme { // Apply your theme here
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Background), // Set background color here (or use your theme color)
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    appTopBar()
                    deviceListView(scannedDevices)
                    Spacer(modifier = Modifier.height(16.dp))
                    scanButton(
                        scanning = deviceController.isScanning, // Pass the scanning state
                        onScanToggle = { deviceController.toggleScanning(this@MainActivity, requestPermissionLauncher) }  // Pass the toggle function
                    )
                }
            }

        }

        deviceScanner.setScanCallback(object : DeviceScanner.ScanCallback {
            override fun onScanResult(devices: List<BtleDevice>) {
                scannedDevices.clear()
                scannedDevices.addAll(devices)
                println("Number of scanned devices: ${scannedDevices.size}")
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        deviceScanner.stopScanning()
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
                Toast.makeText(
                    this@MainActivity,
                    "App will now close. Please restart and grant permissions if you wish to use the app.",
                    Toast.LENGTH_LONG
                ).show()
                // Optionally close the app after a delay here as well
                Handler(Looper.getMainLooper()).postDelayed({
                    finish()
                }, 2000)
            }
            .show()
    }

}