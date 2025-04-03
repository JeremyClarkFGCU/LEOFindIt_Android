package com.example.leofindit.viewModels

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.leofindit.model.BtleDevice
import com.example.leofindit.model.DeviceScanner
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//view model for updating, scanning
class BtleViewModel(application: Application) : AndroidViewModel(application) {
    private val _scannedDevices = MutableStateFlow<List<BtleDevice>>(emptyList())
    val scannedDevices: StateFlow<List<BtleDevice>> = _scannedDevices

    private val _isScanning = MutableStateFlow(false) // Keep track of scanning state
    val isScanning: StateFlow<Boolean> = _isScanning

    private val _signalStrength = MutableStateFlow<Int?>(null) // Track RSSI values
    val signalStrength: StateFlow<Int?> = _signalStrength

    private val deviceScanner = DeviceScanner(application.applicationContext)



    init {
        // Set callback to receive scan results
        deviceScanner.setScanCallback { devices ->
            _scannedDevices.value = devices
        }
    }
    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun startScanning() {
        Log.i("scanner", "Scan Starting...")
        _isScanning.value = true
        viewModelScope.launch {
            deviceScanner.startScanning()
        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun startScanning(targetAddress: String? = null) {
        Log.i("scanner", "Scan Starting...")
        _isScanning.value = true

        viewModelScope.launch {
            deviceScanner.startScanning()
            while (_isScanning.value) { // Keep updating while scanning
                val devices = _scannedDevices.value

                val targetDevice = targetAddress?.let { address ->
                    devices.find { it.deviceAddress == address }
                }

                if (targetDevice != null) {
                    _signalStrength.value = targetDevice.signalStrength ?: -100
                } else {
                    _signalStrength.value = null
                }

                delay(250) // Update every second
            }
        }
    }

        @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun stopScanning() {
        Log.i("scanner", "Scan Stopped.")
        deviceScanner.stopScanning()
        _isScanning.value = false
        Log.i("scanner", "${scannedDevices.value}")

    }
    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun isScanning(): Boolean {
        return deviceScanner.getScanState()
    }

    // Update a device state safely using copy()
    @SuppressLint("SuspiciousIndentation")
    fun updateDeviceState(address: String, isSafe: Boolean, isSuspicious: Boolean) {
        val device: BtleDevice = _scannedDevices.value.find { it.deviceAddress == address }
            ?: throw NoSuchElementException("No device found with address : $address")
                when {
                    isSafe && !isSuspicious -> device.markSafe()
                    isSuspicious && !isSafe -> device.markSuspicious()
                    !isSafe && !isSuspicious -> device.markUnknown()
                }
                device.copy(isSafe = isSafe, isSuspicious = isSuspicious)
        Log.i("Device Call out", "Device: ${device.deviceName}, is suspicious = ${device.getIsSuspicious()}, is safe = ${device.getIsSafe()}")
    }

    fun isDeviceMarked(device: BtleDevice) : Boolean {
        return(!!device.getIsSuspicious() && !!device.getIsSafe())
    }


    // Set nickname
    fun setNickName(address: String, newNickName: String) {
        _scannedDevices.value = _scannedDevices.value.map { device ->
            if (device.deviceAddress == address) {
                Log.i("BtleViewModel", "User renamed ${device.deviceType}: ${device.getNickName()} to $newNickName.")
                device.copy(nickName = newNickName)
            } else device
        }
    }

    //finds device based on device address (~MAC address... not really)
    fun findDevice(address: String): BtleDevice {
        return _scannedDevices.value.find { it.deviceAddress == address }
            ?: throw NoSuchElementException("No device found with address: $address")
    }

}