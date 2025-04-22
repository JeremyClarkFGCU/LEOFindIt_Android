// MainActivity.kt

package com.example.leofindit

import DeviceDetailCard
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
import com.example.leofindit.controller.LeoPermissionHandler
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
import androidx.compose.runtime.*
import com.example.leofindit.model.AppDatabase
import com.example.leofindit.model.BTLEDeviceDao


const val BLUETOOTH_PERMISSIONS_REQUEST_CODE = 101

class MainActivity : ComponentActivity() {
    private lateinit var deviceScanner: DeviceScanner
    private lateinit var permissionHandler: LeoPermissionHandler
    private lateinit var deviceController: DeviceController
    private val scannedDevices = mutableStateListOf<BtleDevice>()
    private var tag = "MainActivity"

    // Declare Activity Result Launcher-> Needed for maintaining Bluetooth permissions.
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>

    private lateinit var btleDeviceDao: BTLEDeviceDao // Declare btleDeviceDao

    override fun onCreate(savedInstanceState: Bundle?) {
        tag = "MainActivity.onCreate()"
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        deviceScanner = DeviceScanner(this)
        permissionHandler = LeoPermissionHandler()

        // Initialize Activity Result Launcher
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                if (permissions.all { it.value }) {
                    Log.d(tag, "requestPermissionLauncher all permissions granted.")
                    deviceController.onPermissionsGranted()
                } else {
                    Log.w(
                        tag,
                        "User denied permissions, retrying permissions request."
                    )
                    deviceController.onPermissionsDenied()
                    showPermissionDeniedDialog()
                }
            }

        val database = AppDatabase.getDatabase(this)  // Get the database instance
        btleDeviceDao = database.btleDeviceDao()  // Get the DAO

        deviceController = DeviceController(deviceScanner, permissionHandler, btleDeviceDao)  // Pass the DAO

        setContent {
            Leo_findit_aosTheme { // This is the Compose theme.
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Background),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    appTopBar()

                    // State to control showing the detail card
                    var selectedDevice by remember { mutableStateOf<BtleDevice?>(null) }

                    if (selectedDevice == null) {
                        deviceListView(
                            devices = scannedDevices,
                            onDeviceClick = { clickedDevice ->
                                selectedDevice = clickedDevice // Update state on click
                                Log.d("MainActivity", "Clicked on: ${clickedDevice.deviceName}")
                            }
                        )
                    } else {
                        DeviceDetailCard(
                            device = selectedDevice!!,
                            onSafeClick = { device ->
                                device.markSafe()
                            },
                            onSuspiciousClick = { device ->
                                device.markSuspicious()
                            },
                            onTargetClick = { device ->
                                device.isTarget = !device.isTarget
                            },
                            onNicknameChange = { newNickname ->
                                deviceController.updateDeviceNickname(selectedDevice!!, newNickname)
                            },
                            onClose = {
                                selectedDevice = null // Callback to close the detail card
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    scanButton(
                        scanning = deviceController.isScanning, // Pass scanning state (change button "scan" <-> "stop"
                        onScanToggle = {
                            deviceController.toggleScanning(
                                this@MainActivity,
                                requestPermissionLauncher
                            )
                        }  // Toggles Scan on button press.
                    )
                }
            }
        }


        deviceScanner.setScanCallback(object : DeviceScanner.ScanCallback {
            var tag = "MainActivity.deviceScanner.setcallback()"
            override fun onScanResult(devices: List<BtleDevice>) {
                scannedDevices.clear()
                scannedDevices.addAll(devices)
                Log.d(tag, "Scanned ${scannedDevices.size} devices.")
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        deviceScanner.stopScanning()
    }

    /** This calls a Dialog box that double-checks that the user wants to deny bluetooth permissions.
     * @return Nothing
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