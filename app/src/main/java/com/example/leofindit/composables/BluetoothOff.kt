package com.example.leofindit.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.leofindit.R
import com.example.leofindit.ui.theme.LeoFindItTheme

@Composable
fun BluetoothOff() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.baseline_warning_24),
            contentDescription = "Bluetooth Error",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = "Bluetooth is off",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "Bluetooth must be on to continue",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.size(0.dp))
        TextButton (
            onClick = {},
        ) {
            Text (
                text ="Older trackers...",
            )
        }
    }
}

@Preview
@Composable
fun BluetoothOffPreview() {
    LeoFindItTheme {
        Surface (modifier = Modifier.fillMaxSize()) {
            BluetoothOff()
        }
    }
}