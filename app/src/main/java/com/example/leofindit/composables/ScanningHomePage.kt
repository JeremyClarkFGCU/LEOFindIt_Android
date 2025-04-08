//
//  ScanningHomePage.kt
//  LeoFindIt
//
//  Created by Brian Zapata Resendiz
package com.example.leofindit.composables

import android.Manifest
import android.annotation.SuppressLint
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation.NavController
import com.example.leofindit.BtHelper
import com.example.leofindit.LocationHelper
import com.example.leofindit.R
import com.example.leofindit.ui.theme.GoldPrimary
import com.example.leofindit.ui.theme.InversePrimary
import com.example.leofindit.ui.theme.LeoFindItTheme
import com.example.leofindit.viewModels.BtleViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi

// change text in 6 seconds
@SuppressLint("StateFlowValueCalledInComposition", "SupportAnnotationUsage", "MissingPermission")
@RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT,
    [Manifest.permission.BLUETOOTH_SCAN]
)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ManualScanning(
    navController : NavController? = null,
    viewModel: BtleViewModel

) {
    // will scan if device is suspended in background code below stops that
   //BT Scanning Consumes a lot of energy Please check for this
    LifecycleEventEffect(Lifecycle.Event.ON_PAUSE){viewModel.stopScanning()}

    //Home page
    val btPermissionsState = BtHelper.rememberPermissions()
    val isBluetoothOn = BtHelper.scanPreCheck(btPermissionsState)
    val locationPermissionState = LocationHelper.rememberLocationPermissionState()
    val isLocationOn = LocationHelper.scanPreCheck(locationPermissionState)

    val scannedDevices by viewModel.scannedDevices.collectAsState(initial = emptyList())
    val isScanning by viewModel.isScanning.collectAsState(initial = false)


    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    )
    {
        item {
            Spacer(Modifier.size(56.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                // Left: Play/Pause Buttons
                Row(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .background(InversePrimary, shape = MaterialTheme.shapes.medium)
                        .padding(8.dp)
                        .align(Alignment.CenterStart)
                ) {
                    // Play Button
                    IconButton(
                        onClick = { viewModel.startScanning() },
                        enabled = isLocationOn && isBluetoothOn,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = if (isScanning) GoldPrimary else Color.Transparent
                        ),
                        modifier = Modifier.padding(end = 12.dp).size(24.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.baseline_play_arrow_24),
                            contentDescription = null,
                            tint = if (isScanning) Color.Black else GoldPrimary
                        )
                    }

                    VerticalDivider(color = Color.LightGray, modifier = Modifier.height(24.dp))

                    // Pause Button
                    IconButton(
                        onClick = { viewModel.stopScanning() },
                        enabled = isLocationOn && isBluetoothOn,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = if (!isScanning) GoldPrimary else Color.Transparent
                        ),
                        modifier = Modifier.padding(start = 12.dp).size(24.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.stop_24dp_e3e3e3_fill1_wght400_grad0_opsz24),
                            contentDescription = null,
                            tint = if (!isScanning && (isBluetoothOn && isLocationOn)) Color.Black else GoldPrimary
                        )
                    }
                }

                // Center: Scan Text
                Text(
                    text = "Scan",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )

                // Right: Settings Icon Button
                IconButton(
                    onClick = {navController?.navigate("Settings") },
                    colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Transparent),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 12.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.outline_settings_24),
                        contentDescription = null,
                        tint = GoldPrimary
                    )
                }
            }
        }


        // Bt and Permissions check
        if (!isBluetoothOn || !isLocationOn) {
            item { BluetoothOff() }
        }
        else {
            //if scanning on then show scanning
            if (isScanning && scannedDevices.isEmpty()) {
                item { Scanning(numberOfTrackers = scannedDevices.size, navController = navController) }
            }
            //else show list
            else {
                itemsIndexed(scannedDevices) { index, device ->
                    DeviceListEntry(navController, device)
                    Spacer(modifier = Modifier.size(8.dp))
                }
                item { Spacer(Modifier.height(24.dp)) }

            }
        }
    }
}

@RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
@Preview
@Composable
fun ManualScanningPreview() {
    LeoFindItTheme {
        Surface(modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ){
            ManualScanning(
                navController = TODO(),
                viewModel = TODO()
            )
        }
    }
}
