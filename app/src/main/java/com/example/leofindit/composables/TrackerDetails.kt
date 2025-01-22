package com.example.proximitytracker.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.leofindit.ui.theme.LeoFindItTheme


@Composable
fun TrackerDetails() {
    var tracker = "Tile"
    var bluetoothData= null
    var clock = null
    var notCurrentlyReachable : Boolean? = false
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
            //map
            Card(modifier = Modifier.height(100.dp).width(400.dp), onClick = {}) {
                Row(){}

            }
        }
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