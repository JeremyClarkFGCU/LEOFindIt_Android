package com.example.proximitytracker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.leofindit.R
import com.example.leofindit.ui.theme.LeoFindItTheme


@Composable
fun TrackerDetails() {
    var tracker = "Tile"
    var bluetoothData = null
    var clock = null
    var notCurrentlyReachable: Boolean? = false
    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.size(56.dp))
        // Device name
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = tracker,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.weight(1f),
                maxLines = 1
            )
        }

        // Connection status and last seen time
        val connectionStatus = true
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (notCurrentlyReachable == true) {
                BasicText(
                    text = "No Connection",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                if (connectionStatus == null) {
                    Text(
                        text = "connectionStatus.description",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Text(
                text = "Last seen: Just Now",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.fillMaxWidth()
            )
            //map TBA
            Card(modifier = Modifier.height(100.dp).width(400.dp), onClick = {}) {
                Row() {}

            }
            // list of options
            Column() {
//                    RoundedListItem(
//                        headline = "Locate Tracker",
//                        trailingContent = {}
//                    )
//                    RoundedListItem(
//                        headline = "Locate Tracker",
//                        trailingContent = {}
//                    )
                        ListItem(
                            headlineContent = { Text("Locate Tracker") },
                            trailingContent = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "Nearby",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                        contentDescription = "null",
                                        tint = Color.Gray
                                    )
                                }
                            }
                        )
                        ListItem(
                            headlineContent = { Text("Observe Tracker") },
                            trailingContent = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "Off",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                        contentDescription = "null",
                                        tint = Color.Gray
                                    )
                                }
                            }
                        )
                        ListItem(
                            headlineContent = { Text("Ignore Tracker") },
                            trailingContent = {
                                Switch(
                                    onCheckedChange = {},
                                    checked = false

                                )
                            }
                        )
                    }

                Text(
                    text = "Ignoring trackers will stop notifications in the background",
                    style = MaterialTheme.typography.labelSmall
                )
                Box(Modifier.background(shape = AbsoluteCutCornerShape(15), color = Color.Black)) {
                    Column() {
                        ListItem(
                            headlineContent = { Text("Manufacture's Website") },
                            trailingContent = {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.baseline_link_24),
                                    contentDescription = "null",
                                    tint = Color.Gray,
                                    modifier = Modifier.rotate(-45f)
                                )
                            }
                        )
                    }
                }
                Text(
                    text = " Learn more, e.g how to disable the tracker"
                )
            }
        }
    }

@Composable
fun RoundedListItem(
    listItem : @Composable () -> Unit,
    onClick :  () -> Unit,
) {
    Card(
        onClick = {onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        listItem()
    }
}

@Preview
@Composable
fun TrackerDetailsPreview() {
    LeoFindItTheme {
        Surface (modifier = Modifier.fillMaxSize()){
            TrackerDetails()
        }
    }
}