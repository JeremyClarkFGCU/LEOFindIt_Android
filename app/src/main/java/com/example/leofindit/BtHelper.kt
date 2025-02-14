package com.example.leofindit

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context

object BtHelper {
    private var bluetoothManager : BluetoothManager? = null
    private var bluetoothAdapter: BluetoothAdapter? = null

    fun init(context: Context) {
        bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager?.adapter
    }

    fun isEnabled() : Boolean {
        return bluetoothAdapter?.isEnabled ?: false
    }

    fun getAdapter() : BluetoothAdapter? {
        return bluetoothAdapter
    }
    fun request() {

    }
}