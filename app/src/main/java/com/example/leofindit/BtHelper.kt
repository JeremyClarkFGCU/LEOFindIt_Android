package com.example.leofindit
import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import com.example.leofindit.LocationHelper.isLocationServiceEnabled
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState

object BtHelper {
    private var bluetoothManager : BluetoothManager? = null
    private var bluetoothAdapter: BluetoothAdapter? = null

    private val bluetoothPermissions = listOf(
    Manifest.permission.BLUETOOTH_ADMIN,
    Manifest.permission.BLUETOOTH_CONNECT,
    Manifest.permission.BLUETOOTH_SCAN,
    )

    fun init(context: Context) {
        bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager?.adapter
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun rememberPermissions(): MultiplePermissionsState {
        return rememberMultiplePermissionsState(permissions = bluetoothPermissions)
    }

    // Constantly checking is the state of BT service is turned off or on
    //made to solve issue of text not changing when changed
    @Composable
    fun checkingBtEnabledState() : State<Boolean> {
        val context = LocalContext.current
        return produceState(initialValue = isBtEnabled()) {

            val checkBtStatus = {
                value = isBtEnabled()
            }

            val receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    checkBtStatus()
                }
            }

            context.registerReceiver(receiver, IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED))
            checkBtStatus()

            awaitDispose {
                context.unregisterReceiver(receiver)
            }
        }
    }
    fun isBtEnabled() : Boolean {
        return bluetoothAdapter?.isEnabled == true
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun turnOnBtService (context : Context) {
        val requestBluetoothService = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        context.startActivity(requestBluetoothService,)

    }

    fun getAdapter() : BluetoothAdapter? {
        return bluetoothAdapter
    }
    fun request() {

    }
}