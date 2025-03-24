package com.example.leofindit.view
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import com.example.leofindit.model.BtleDevice
import com.example.leofindit.ui.theme.*


@Composable
fun deviceListView(devices: List<BtleDevice>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 700.dp)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally// Center the card
    ) {
        // Scrollable List Container (Light Tan Card)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Take up remaining space
                .clip(RoundedCornerShape(0.dp)), // Rounded corners
            colors = CardDefaults.cardColors(containerColor = OnBackground) // Light beige color
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(devices) { device ->
                    deviceCard(device = device) // Display each device in a card
                }
            }
        }
    }
}