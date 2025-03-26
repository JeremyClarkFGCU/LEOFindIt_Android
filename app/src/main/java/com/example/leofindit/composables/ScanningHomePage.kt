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
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.leofindit.BtHelper
import com.example.leofindit.R
import com.example.leofindit.ui.theme.GoldPrimary
import com.example.leofindit.ui.theme.InversePrimary
import com.example.leofindit.ui.theme.LeoFindItTheme
import com.example.leofindit.viewModels.BtleViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi

// change text in 6 seconds
@SuppressLint("StateFlowValueCalledInComposition")
@RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
@OptIn(ExperimentalPermissionsApi::class)

@Composable
fun ManualScanning(
    navController : NavController? = null,
    viewModel: BtleViewModel

) {
    //Home page
    val permissionsState = BtHelper.rememberPermissions()
    val isBluetoothOn = BtHelper.scanPreCheck(permissionsState)

    val scannedDevices by viewModel.scannedDevices.collectAsState(initial = emptyList())
    var isScanning by remember { mutableStateOf(true) }
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    )
    {
        item {
            Spacer(Modifier.size(56.dp))
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Row(
                        modifier = Modifier
                            .padding(start=12.dp)
                            .background(InversePrimary, shape = MaterialTheme.shapes.medium)
                            .padding(8.dp)
                            .align(Alignment.CenterStart)
                    )
                    {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.baseline_play_arrow_24),
                            contentDescription = null,
                            tint = GoldPrimary,
                            modifier = Modifier.padding(end = 12.dp)
                                .clickable(onClick =  {
                                    viewModel.startScanning()
                                })
                        )
                        VerticalDivider(color = Color.LightGray, modifier = Modifier.height(24.dp))
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.baseline_pause_24),
                            contentDescription = null,
                            tint = GoldPrimary,
                            modifier = Modifier.padding(start = 12.dp)
                                .clickable(onClick = {
                                    //stop scan
                                })
                        )
                    }
                    Text(
                        text = "Scan",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                    IconButton(onClick = {
                        //todo navigate to settings
                    },
                        modifier = Modifier.align(Alignment.CenterEnd).padding(end = 12.dp),
                        ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.outline_settings_24),
                            contentDescription = null,
                            modifier = Modifier.align(Alignment.CenterEnd).padding(end = 12.dp),
                            tint = GoldPrimary
                        )
                    }

                }
        }
        if (!isBluetoothOn) {
            item { BluetoothOff(permissionsState) }
        }
        else {
            //if scanning on then show scanning
            if (!isScanning) {
                item { Scanning() }
            }
            //else show list
            else {
                itemsIndexed(scannedDevices) { index, device ->
                    DeviceListEntry(navController, device, device.deviceAddress ?: "No address #$index")
                    Spacer(modifier = Modifier.size(8.dp))
                }

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
