package com.example.leofindit.composables

import android.Manifest
import android.content.Intent
import android.util.Log
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.leofindit.LocationHelper.rememberLocationEnabledState
import com.example.leofindit.LocationHelper.rememberLocationPermissionState
import com.example.leofindit.R
import com.example.leofindit.ui.theme.LeoFindItTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun LocationAccess(navController: NavController? = null) {

    val permissionsState = rememberLocationPermissionState()
    val context = LocalContext.current
    val isLocationOn by rememberLocationEnabledState()
//    val isLocationOn by produceState(initialValue = LocationHelper.isLocationServiceEnabled()) {
//        value = LocationHelper.isLocationServiceEnabled()
//    }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize()
    ){
        Text(
            text = "Location Access",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.baseline_location_pin_24),
            contentDescription = "Location Pin",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = "We use the location of your device to determine if a tracker is following" +
                    " you. All location data stays on device. Please tap \"Continue\" and" +
                    " select \"Allow While Using App\". Make sure \"Precise\" is turned on.  ",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start= 8.dp, end = 8.dp)
        )
        Button(
            onClick = {
                   // LocationHelper.RequestPermission()//permissionsState.launchMultiplePermissionRequest()
                when {
                   permissionsState.allPermissionsGranted && LocationHelper.isLocationServiceEnabled() -> {
                       navController?.navigate("Bluetooth Permission")
                   }
                    !permissionsState.allPermissionsGranted -> {
                        permissionsState.launchMultiplePermissionRequest()
                    }
                    !isLocationOn -> {
                        LocationHelper.enableLocationService(context)
                        Log.i("Location", "Location service is not on")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(.75f)

        ) {
            Text(
               text =  when {
                    permissionsState.allPermissionsGranted && LocationHelper.isLocationServiceEnabled() -> {
                        "Continue"
                    }
                    !permissionsState.allPermissionsGranted -> {
                        "Request Permission"
                    }
                    !isLocationOn -> {
                        "Turn on Location"
                    }

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