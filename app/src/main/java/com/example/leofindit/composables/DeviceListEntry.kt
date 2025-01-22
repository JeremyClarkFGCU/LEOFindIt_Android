package com.example.leofindit.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.Navigator
import com.example.leofindit.R
import com.example.leofindit.ui.theme.LeoFindItTheme
//todo pass device object
//samsung tag for example only
@Composable
fun DeviceListEntry(navController: NavController? = null, deviceName: String? = "Samsung Tag") {
    Card(
        modifier = Modifier.size(width = 360.dp, height = 40.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.onSurface
//        ),
        onClick = {
            navController?.navigate(route ="Tracker Details")
        }
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left-side content
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.samsung_icon),
                    contentDescription = "Device Type Icon",
                    tint = Color.Unspecified
                )
                Column {
                    Text(
                        text = "$deviceName",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.width(192.dp),
                    )
                }
            }
            Row {

                // Signal strength icon aligned to the end
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_signal_cellular_alt_24),
                    contentDescription = "Signal Strength",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(40.dp)
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Signal Strength",
                    tint = Color.Gray,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun DeviceDetailEntryPreview() {
    LeoFindItTheme {
        Surface {
            DeviceListEntry()
        }
    }
}