package com.example.leofindit.composables.introduction

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.leofindit.R
import com.example.leofindit.ui.theme.GoldPrimary
import com.example.leofindit.ui.theme.GoldPrimaryDull
import com.example.leofindit.ui.theme.LeoFindItTheme
import com.example.leofindit.ui.theme.OnSurface
import com.example.leofindit.ui.theme.Surface
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NotificationPermission(navController: NavController? = null) {
    val notificationPermissionList = rememberPermissionState(
        permission =
            Manifest.permission.POST_NOTIFICATIONS
    )
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize()
    ) {

        Text(
            text = "Notifications Access",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            color = GoldPrimary
        )
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.baseline_notifications_24),
            contentDescription = "Notification Logo",
            tint = GoldPrimaryDull,
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = "To receive a notification if your might have been tracked, you need to " +
                    "enable notifications for Proximity Tracker",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start= 8.dp, end = 8.dp),
            color = GoldPrimary
        )
        Column {
            TextButton (
                onClick = { navController?.navigate("Permission Done") },
                modifier = Modifier.fillMaxWidth(.75f)

            ) {
                Text(
                    text = "Skip",
                    color = GoldPrimary
                )
            }
            Button(
                onClick = {
                    if (!notificationPermissionList.status.isGranted) {
                        notificationPermissionList.launchPermissionRequest()
                    } else {
                        navController?.navigate("Permission Done")
                    }

                },
                colors = ButtonDefaults.buttonColors(containerColor = Surface, contentColor = OnSurface),
                modifier = Modifier.fillMaxWidth(.75f)

            ) {
                Text(
                    text = "Continue",
                )
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
fun NotificationPermissionPreview() {
    LeoFindItTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            NotificationPermission()
        }
    }
}