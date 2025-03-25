package com.example.leofindit.composables

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.leofindit.BtHelper
import com.example.leofindit.R
import com.example.leofindit.ui.theme.GoldPrimary
import com.example.leofindit.ui.theme.LeoFindItTheme
import com.example.leofindit.ui.theme.OnPrimary
import com.example.leofindit.ui.theme.OnSurface
import com.example.leofindit.ui.theme.Surface
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState

@RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BluetoothOff(permissionsState : MultiplePermissionsState) {
    val permissionsState = BtHelper.rememberPermissions()
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.baseline_warning_24),
            contentDescription = "Bluetooth Error",
            tint = OnPrimary,
            modifier = Modifier.size(100.dp)
        )
        Text(
            color = GoldPrimary,
            text = "Bluetooth is off",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            color = GoldPrimary,
            text = "Bluetooth must be on to continue",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.size(0.dp))
        Row(horizontalArrangement = Arrangement.Center) {
            if (!BtHelper.checkingBtPermissionState(permissionsState).value) {
                Button(
                    onClick = { BtHelper.requestPermission(permissionsState, context) },
                    modifier = Modifier.padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Surface, contentColor = Color.LightGray)

                ) {
                    Text(
                        text = "Accept Permissions",
                        maxLines = 1
                    )
                }
            }
            if (!BtHelper.checkingBtEnabledState().value) {

                Button(
                    onClick =  { BtHelper.turnOnBtService(context) },
                    enabled = permissionsState.allPermissionsGranted,
                    colors = ButtonDefaults.buttonColors(containerColor = Surface, contentColor = OnSurface)

                ) {
                    Text(
                        text = "Turn on Bt Service",
                        maxLines = 1
                    )
                }
            }
        }
        TextButton (
            onClick = {},
        ) {
            Text (
                color = OnSurface,
                text ="Older trackers...",
            )
        }
    }
}
@OptIn(ExperimentalPermissionsApi::class)
@RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
@Preview
@Composable
fun BluetoothOffPreview() {
    LeoFindItTheme {
        Surface (modifier = Modifier.fillMaxSize()) {
            BluetoothOff(permissionsState = BtHelper.rememberPermissions())
        }
    }
}