package com.example.leofindit.viewModels

import android.Manifest
import android.app.Application
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leofindit.model.BtleDevice
import com.example.leofindit.model.DeviceScanner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//view model for updating, scanning
class BtleViewModel(application: Application) : AndroidViewModel(application) {
    private val _scannedDevices = MutableStateFlow<List<BtleDevice>>(emptyList())
    val scannedDevices: StateFlow<List<BtleDevice>> = _scannedDevices
    private val _isScanning = MutableStateFlow(false) // Keep track of scanning state
    val isScanning: StateFlow<Boolean> = _isScanning
    private val deviceScanner = DeviceScanner(application.applicationContext)

    init {
        // Set callback to receive scan results
        deviceScanner.setScanCallback { devices ->
            _scannedDevices.value = devices
        }
    }
    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun startScanning() {
        viewModelScope.launch {
            deviceScanner.startScanning()
        }
        _isScanning.value = true
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun stopScanning() {
        deviceScanner.stopScanning()
        _isScanning.value = false
        Log.i("tags", "${scannedDevices.value}")

    }
    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun isScanning(): Boolean {
        return deviceScanner.getScanState()
    }

    // Update a device state safely using copy()
    fun updateDeviceState(address: String, isSafe: Boolean, isSuspicious: Boolean) {
        _scannedDevices.value = _scannedDevices.value.map { device ->
            if (device.deviceAddress == address) {
                device.copy(isSafe = isSafe, isSuspicious = isSuspicious)
            } else device
        }
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
}