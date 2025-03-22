package com.example.leofindit.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.leofindit.model.BtleDevice
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import com.example.leofindit.ui.theme.LeoIcons
import com.example.leofindit.ui.theme.*
import androidx.compose.foundation.clickable // Import clickable Modifier

@Composable
fun deviceCard(device: BtleDevice, onClick: (BtleDevice) -> Unit) { // Add onClick parameter
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick(device) }, // Wrap the Card with clickable
        colors = CardDefaults.cardColors(containerColor = Surface) // Dark Grey for Card Display (DarkMode
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Top Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left: Icon
                Icon(
                    LeoIcons.Bluetooth, // Or a device-specific icon later
                    contentDescription = "Bluetooth Icon",
                    tint = GoldPrimary // Adjust color as needed
                )
                Spacer(modifier = Modifier.width(8.dp))

                // Right: Device Name
                Text(
                    text = device.getNickName().toString(),
                    fontSize = 20.sp, // Adjust size as needed
                    color = GoldPrimary, // Adjust color as needed
                    modifier = Modifier.weight(1f) // Take up remaining width
                )
            }

            // Bottom Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left: Device Attributes (Address, RSSI)
                Column {
                    Text(
                        text = "UUID: ${device.deviceUuid}",
                        fontSize = 10  .sp, // Adjust size as needed
                        color = OnSurface // Lighter color for dark Card BG
                    )
                    Text(
                        text = "RSSI: ${device.signalStrength} dBm",
                        color = OnSurface // Lighter color for dark Card BG
                    )
                }

                Spacer(modifier = Modifier.weight(1f)) // Push the icon to the right

                // Right: Signal Strength Icon
                val signalStrengthIcon = when {
                    device.signalStrength!! >= -50 -> LeoIcons.SignalStrengthHigh
                    device.signalStrength >= -70 -> LeoIcons.SignalStrengthMed
                    else -> LeoIcons.SignalStrengthLow
                }

                Icon(
                    imageVector = signalStrengthIcon,
                    contentDescription = "Signal Strength",
                    tint = Color.Green // Adjust color as needed
                )
            }
        }
    }
}