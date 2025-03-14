package com.example.leofindit.composables

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.leofindit.BtHelper
import com.example.leofindit.R
import com.example.leofindit.ui.theme.LeoFindItTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState

@Composable
@OptIn(ExperimentalPermissionsApi::class)
@androidx.annotation.RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
fun BluetoothPermission(navController: NavController? = null) {

    val permissionsState = BtHelper.rememberPermissions()
    val requestBluetoothService = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
    val btEnabled by BtHelper.checkingBtEnabledState()
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Bluetooth Access",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.baseline_bluetooth_24),
            contentDescription = "Bluetooth Logo",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = "Trackers use Bluetooth for communication. Please allow Bluetooth access " +
                    "so Proximity Tracker can detect trackers around you",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )
        Button(
            onClick = {
                Log.i(
                    "check",
                    "Bt service granted: ${BtHelper.isBtEnabled()}, " +
                    "Revoked permissions: ${
                        permissionsState.revokedPermissions.map { it.permission }
                    }"
                )
                //permission.launchPermissionRequest()

                when {
                    // Request permissions if they aren't granted
                    !permissionsState.allPermissionsGranted -> {
                        permissionsState.launchMultiplePermissionRequest()
                        //permission.launchPermissionRequest()
                    }

                    // If permissions are granted but Bluetooth is off, request to enable it
                    !BtHelper.isBtEnabled() -> {
                        BtHelper.turnOnBtService(context)
                    }

                    // If everything is granted and enabled, move to the next screen
                    permissionsState.allPermissionsGranted && BtHelper.isBtEnabled() -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            navController?.navigate("Notification Access")
                        }
                        else navController?.navigate("Permission Done")
                    }

                }


            },
            modifier = Modifier.fillMaxWidth(0.75f)
        ) {
            Text(
                when {// Request permissions if they aren't granted
                    !permissionsState.allPermissionsGranted -> {
                        "Request Bluetooth Permissions"
                    }

                    // If permissions are granted but Bluetooth is off, request to enable it
                     !btEnabled -> {
                        "Turn on Bluetooth Service"
                    }

                    // If everything is granted and enabled, move to the next screen
                    btEnabled -> {
                        "Continue"
                    }

                    else -> {"error"}
                }
            )
        }
    }
}

@Preview
@Composable
@androidx.annotation.RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
fun BluetoothPermissionPreview() {
    LeoFindItTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            BluetoothPermission()
        }
    }
}