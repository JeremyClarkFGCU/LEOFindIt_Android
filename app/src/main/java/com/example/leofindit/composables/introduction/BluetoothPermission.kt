package com.example.leofindit.composables.introduction

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.leofindit.ui.theme.GoldPrimary
import com.example.leofindit.ui.theme.GoldPrimaryDull
import com.example.leofindit.ui.theme.LeoFindItTheme
import com.example.leofindit.ui.theme.OnSurface
import com.example.leofindit.ui.theme.Surface
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@Composable
@OptIn(ExperimentalPermissionsApi::class)
@RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
fun BluetoothPermission(navController: NavController? = null) {

    val permissionsState = BtHelper.rememberPermissions()
    val btEnabled by BtHelper.checkingBtEnabledState()
    val context = LocalContext.current

    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        val uri = Uri.fromParts("package",context.packageName, null )
        data = uri
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Bluetooth Access",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            color = GoldPrimary,
        )
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.baseline_bluetooth_24),
            contentDescription = "Bluetooth Logo",
            tint = GoldPrimaryDull,
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = "Trackers use Bluetooth for communication. Please allow Bluetooth access " +
                    "so Proximity Tracker can detect trackers around you",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            color = GoldPrimary
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
//                BtHelper.CheckBt(permissionsState = permissionsState, context =  context) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                            navController?.navigate("Notification Access")
//                        }
//                        else navController?.navigate("Permission Done")
//                }

                //permission.launchPermissionRequest()

                when {
                    // Request permissions if they aren't granted
                    !permissionsState.allPermissionsGranted -> {

                        permissionsState.launchMultiplePermissionRequest()
                        if (!permissionsState.shouldShowRationale) {
                            permissionsState.launchMultiplePermissionRequest()
                        }
                        else {
                            Toast.makeText(
                                context, "Permission denied, please go into settings to enable " +
                                        "permissions", Toast.LENGTH_LONG
                            ).show()
                            context.startActivity(intent)
                        }
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
            colors = ButtonDefaults.buttonColors(containerColor = Surface, contentColor = OnSurface),
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
                },
                color= GoldPrimary
            )
        }
    }
}

@Preview
@Composable
@RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
fun BluetoothPermissionPreview() {
    LeoFindItTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            BluetoothPermission()
        }
    }
}