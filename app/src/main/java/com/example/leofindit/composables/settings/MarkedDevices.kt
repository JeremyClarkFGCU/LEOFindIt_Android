package com.example.leofindit.composables.settings

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.leofindit.R
import com.example.leofindit.composables.DeviceDetailEntryPreview
import com.example.leofindit.ui.theme.Background
import com.example.leofindit.ui.theme.GoldPrimary
import com.example.leofindit.ui.theme.GoldPrimaryDull
import com.example.leofindit.ui.theme.LeoFindItTheme
import com.example.leofindit.ui.theme.Surface

@Composable
fun MarkedDevices(navController: NavController? = null) {
    val items = listOf("Black List Devices", "White List Devices")
    var expandedItem by remember { mutableIntStateOf(-1) } // Track which item is expanded

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Spacer(Modifier.size(56.dp))
            Box(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                // Back Button
                IconButton(
                    onClick = { navController?.popBackStack() },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_arrow_back_24),
                        contentDescription = "Back Arrow",
                        tint = GoldPrimary
                    )
                }

                // Center Title
                Text(
                    text = "Marked Devices",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        itemsIndexed(items) { index, item ->
            ExpandableCard(
                title = item,
                isExpanded = expandedItem == index,
                onClick = { expandedItem = if (expandedItem == index) -1 else index }
            )
            {
                if (item == "Black List Devices") {
                    repeat(3) {
                        DeviceDetailEntryPreview()
                        //todo: get From BlackList data base
                    }
                } else {
                    repeat(10) {
                        DeviceDetailEntryPreview()
                        //todo: get From WhiteList List data base
                    }
                }
            }
        }
    }
}

@Composable
fun ExpandableCard(title: String, isExpanded: Boolean, onClick: () -> Unit, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween,) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldPrimaryDull
                )
                Icon(
                    imageVector = if(isExpanded)ImageVector.vectorResource(R.drawable.baseline_arrow_drop_up_24) else ImageVector.vectorResource(R.drawable.baseline_arrow_drop_down_24),
                    contentDescription = "Back Arrow",
                    )
            }
            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 8.dp)) {

                        content()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(
    device = "spec:width=411dp,height=891dp,cutout=punch_hole,navigation=buttons",
    showSystemUi = true
)
@Composable
fun BlackListPreview() {
    LeoFindItTheme {
        Scaffold(modifier = Modifier.background(color = Background)) {
            MarkedDevices()
        }
    }
}
