package com.example.leofindit.composables.introduction

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
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
import com.example.leofindit.LocationHelper
import com.example.leofindit.LocationHelper.checkingLocationEnabledState
import com.example.leofindit.LocationHelper.rememberLocationPermissionState
import com.example.leofindit.R
import com.example.leofindit.ui.theme.GoldPrimary
import com.example.leofindit.ui.theme.GoldPrimaryDull
import com.example.leofindit.ui.theme.LeoFindItTheme
import com.example.leofindit.ui.theme.OnSurface
import com.example.leofindit.ui.theme.Surface
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun LocationAccess(navController: NavController? = null) {

    val context = LocalContext.current
    val permissionsState = rememberLocationPermissionState()
    val isLocationOn by checkingLocationEnabledState()

    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        val uri = Uri.fromParts("package",context.packageName, null )
        data = uri
    }
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize()
    ){
        Text(
            text = "Location Access",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            color = GoldPrimary
        )
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.baseline_location_pin_24),
            contentDescription = "Location Pin",
            tint = GoldPrimaryDull,
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = "We use the location of your device to determine if a tracker is following" +
                    " you. All location data stays on device. Please tap \"Continue\" and" +
                    " select \"Allow While Using App\". Make sure \"Precise\" is turned on.  ",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start= 8.dp, end = 8.dp),
            color = GoldPrimary
        )
        Button(
            onClick = {
                when {
                    !permissionsState.allPermissionsGranted -> {
                        if (!permissionsState.shouldShowRationale) {
                            permissionsState.launchMultiplePermissionRequest()
                        }
                        else {
                            Toast.makeText(context, "Permission denied, please go into settings to enable " +
                                    "permissions", Toast.LENGTH_LONG).show()
                            context.startActivity(intent)
                        }
                    }
                    !isLocationOn -> {
                        LocationHelper.enableLocationService(context)
                    }
                   permissionsState.allPermissionsGranted && LocationHelper.isLocationServiceEnabled() -> {
                       navController?.navigate("Bluetooth Permission")
                   }
                }
            },
            modifier = Modifier.fillMaxWidth(.75f),
            colors = ButtonDefaults.buttonColors(containerColor = Surface, contentColor = OnSurface)


        ) {
            Text(
               text =  when {
                    !permissionsState.allPermissionsGranted -> "Request Permission"
                    !isLocationOn -> "Turn on Location"
                    permissionsState.allPermissionsGranted && LocationHelper.isLocationServiceEnabled() ->
                        "Continue"
                   else -> {"error"}
               }
            )
        }


    }

}
@Preview
@Composable
fun LocationAccessPreview() {
    LeoFindItTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            LocationAccess()
        }
    }
}