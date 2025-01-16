package com.example.leofindit.composables

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
import com.example.leofindit.ui.theme.LeoFindItTheme

@Composable
fun BluetoothPermission(navController: NavController? = null) {
    Column (
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
            modifier = Modifier.padding(start= 8.dp, end = 8.dp)
        )
        Button(
            onClick = {navController?.navigate("Notification Access")},
            modifier = Modifier.fillMaxWidth(.75f)

        ) {
            Text(
                text = "Continue"
            )
        }
    }
}

@Preview
@Composable
fun BluetoothPermissionPreview() {
    LeoFindItTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            BluetoothPermission()
        }
    }
}