package com.example.leofindit.model

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanResult
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.Manifest
import android.content.pm.PackageManager
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import androidx.core.util.isNotEmpty


class DeviceScanner(private val context: Context) {

    var tag: String? = "DeviceScanner"

    // Initialize Bluetooth interface(s)
    private val bluetoothManager: BluetoothManager =
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
    private val bluetoothLeScanner: BluetoothLeScanner? = bluetoothAdapter?.bluetoothLeScanner
    private var isScanning = false

    /**
     * @Note Disabled until logic completed for only storing Blackist/Whitelist
    // Initialize Database for persistent device storage
    private val database = AppDatabase.getDatabase(context)
    private val btleDeviceDao = database.btleDeviceDao()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)  // Use IO dispatcher for DB
*/

    init {
        Log.d(tag, "BluetoothAdapter: $bluetoothAdapter")
        Log.d(tag, "BluetoothLeScanner: $bluetoothLeScanner")
        isScanning = false
    }


    private val scanResults = mutableListOf<BtleDevice>() // Changed to BTLEDevice

    // Callback interface to notify about scan results
    interface ScanCallback {
        fun onScanResult(devices: List<BtleDevice>) // Changed to BTLEDevice
    }
    private var scanCallback: ((List<BtleDevice>) -> Unit)? = null

    fun setScanCallback(callback: (List<BtleDevice>) -> Unit) {
        scanCallback = callback
    }
//    private var scanCallback: ScanCallback? = null
//
//    fun setScanCallback(callback: ScanCallback) {
//        scanCallback = callback
//    }

    private val leScanCallback = object : android.bluetooth.le.ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val device = result.device
            val rssi = result.rssi

            val deviceName =
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    device.name ?: "Unknown Device"
                } else {
                    "Unknown Device (No BT Connect Permission)"
                }

            val deviceAddress = device.address ?: "Unknown" // Get the MAC address

            // Get manufacturer-specific data
            val manufacturerData = result.scanRecord?.getManufacturerSpecificData()
            val manufacturer = if (manufacturerData != null && manufacturerData.isNotEmpty()) {
                // Assuming you want the first manufacturer ID's data
                val manufacturerId = manufacturerData.keyAt(0)
                val manufacturerSpecificData = manufacturerData[manufacturerId]
                manufacturerSpecificData?.joinToString("") { "%02X".format(it) }
                    ?: "Unknown" // Convert bytes to hex string
            } else {
                "Unknown"
            }

            // Check if a device with this address already exists
            val existingDeviceIndex =
                scanResults.indexOfFirst { it.deviceAddress == deviceAddress }

            val btleDevice = BtleDevice( // Changed to BTLEDevice
                deviceType = "Tag",
                deviceManufacturer = manufacturer,
                deviceName = deviceName,
                deviceAddress = deviceAddress,
                signalStrength = rssi,
                isSafe = false,
                isParent = false,
                isTarget = false,
                isSuspicious = false,
                isTag = false,
                timeStamp = System.currentTimeMillis(),
                nickName = deviceName
            )
/**
 * @Note: This is commented out until logic implemented to store Only Blacklist/Whitelist data persistently.
            CoroutineScope(Dispatchers.IO).launch {
                // Safely create deviceEntity
                if (btleDevice.deviceAddress != null && btleDevice.signalStrength != null) {
                    val deviceEntity = BTLEDeviceEntity(
                        deviceAddress = btleDevice.deviceAddress,
                        deviceManufacturer = btleDevice.deviceManufacturer,
                        deviceType = btleDevice.deviceType,
                        signalStrength = btleDevice.signalStrength,
                        isSafe = btleDevice.getIsSafe(),
                        isSuspicious = btleDevice.getIsSuspicious()
                    )

                    val existingDevice =
                        btleDeviceDao.getDeviceByAddress(deviceEntity.deviceAddress)
                    if (existingDevice == null) {
                        btleDeviceDao.insert(deviceEntity)
                    } else {
                        btleDeviceDao.update(deviceEntity) // Update instead of insert
                    }
                } else {
                    Log.e(tag, "deviceAddress or signalStrength is null for ${btleDevice.deviceName}")
                    // Handle the case where deviceAddress or signalStrength is null
                    // You might want to log this, or skip saving the device to the database
                }
            }// End of CoRoutine
            */ //End of commented out code.


            // Update the scanResults list
            if (existingDeviceIndex == -1) {
                // Device is new, add it to the list
                scanResults.add(btleDevice)
            } else {
                // Device exists, update its data (e.g., RSSI)
                scanResults[existingDeviceIndex] = btleDevice
            }
            //scanCallback?.onScanResult(scanResults)
            scanCallback?.invoke((scanResults.toList()))
        }

        override fun onScanFailed(errorCode: Int) {
            Log.e(tag, "Scan failed with error: $errorCode")
        }
    }// End of leScanCallback

    /**
     * This function contains the core logic for calling system method to start BT Scan.
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun startScanning() {
        Log.d("DeviceScanner", "startScanning called")
        if (isScanning) return

        if (bluetoothAdapter?.isEnabled == false) {
            Log.e("DeviceScanner", "Bluetooth is not enabled")
            return
        }

        isScanning = true
        scanResults.clear() // Clear existing results

        bluetoothLeScanner?.startScan(leScanCallback)

        // Stop scanning after a defined period
       // handler.postDelayed({stopScanning()}, 60000)

    } // End of startScanning() function

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun stopScanning() {
        tag = "DeviceScanner.stopScanning()"
        Log.d(tag, "stopScanning called")
        if (!isScanning) return
        isScanning = false
        bluetoothLeScanner?.stopScan(leScanCallback)
    }// End of stopScanning()

    fun getScanState():Boolean{
        return isScanning
    }
} // End of DeviceScanner Class