package com.example.leofindit
import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
/*********************************************************************************
 *                   Used to detect and handel Bluetooth Permissions,
 *                   Services, also sets adapter and Manager on init
 *********************************************************************************/
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
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun checkingBtPermissionState(permissionsState: MultiplePermissionsState): State<Boolean> {
        val hasPermissions = remember { mutableStateOf(permissionsState.allPermissionsGranted) }

        LaunchedEffect(permissionsState.revokedPermissions) {
            hasPermissions.value = permissionsState.allPermissionsGranted
        }

        return hasPermissions
    }

    fun isBtEnabled() : Boolean {
        return bluetoothAdapter?.isEnabled == true
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun turnOnBtService (context : Context) {
        val requestBluetoothService = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        context.startActivity(requestBluetoothService,)

    }

    //function returns true if Both Bluetooth Service and Permissions are granted
    @Composable
    @OptIn(ExperimentalPermissionsApi::class)
    fun scanPreCheck(permissionsState: MultiplePermissionsState) : Boolean {
        val isBluetoothEnabled by checkingBtEnabledState()
        val hasPermissions by checkingBtPermissionState(permissionsState)
        return isBluetoothEnabled && hasPermissions
    }
    @OptIn(ExperimentalPermissionsApi::class)
    @SuppressLint("PermissionLaunchedDuringComposition")
    fun requestPermission(permissionsState: MultiplePermissionsState, context: Context ) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            val uri = Uri.fromParts("package",context.packageName, null )
            data = uri
        }
            if (!permissionsState.shouldShowRationale) {
                permissionsState.launchMultiplePermissionRequest()
            }
            else {
                Toast.makeText(context, "Permission denied, please go into settings to enable " +
                        "permissions", Toast.LENGTH_LONG).show()
                context.startActivity(intent)
            }
    }




    fun getAdapter() : BluetoothAdapter? {
        return bluetoothAdapter
    }
}