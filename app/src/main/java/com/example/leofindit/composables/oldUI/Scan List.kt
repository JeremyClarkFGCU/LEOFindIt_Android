package com.example.leofindit.composables.oldUI

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.leofindit.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScanList (modifier: Modifier = Modifier, navController: NavController) {
    var scan  by remember{ mutableStateOf(true) }

        Scaffold(
            modifier = modifier,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        scan = !scan
                        /* todo add scan */
                    },
                    containerColor = Color(0xff2e2921),
                    modifier = Modifier.offset(y = (48).dp)
                ) {
                    if (scan) {
                        Icon(
                            Icons.Filled.PlayArrow,
                            contentDescription = "Play Button",
                            tint = Color(0xffe9c16c)
                        )
                    } else {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.baseline_pause_24),
                            contentDescription = "Pause Button",
                            tint = Color.Unspecified
                        )
                    }
                }
            },
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xffdebb00), Color(0xff000000))
                        )
                    ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item { Spacer(modifier = Modifier.padding(8.dp)) }
                items(count = 30) {
                    Card(
                        modifier = Modifier.size(width = 360.dp, height = 80.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        onClick = {
                            navController.navigate(
                                route = "Device Details/${"Device Name and or UUID"}/${"000.000.00.0"}"
                            )
                        }
                    ) {
                        Row {
                            Row(
                                Modifier.padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(
                                    16.dp,
                                    Alignment.Start
                                ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.baseline_signal_cellular_alt_24),
                                    contentDescription = "Signal Strength",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(40.dp)
                                )
                                Column {
                                    Text(
                                        text = "Device Name",
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.width(192.dp),
                                    )
                                    Text(
                                        text = "Type",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.width(192.dp).height(20.dp),

                                        )
                                }
                            }
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.airtag_icon),
                                contentDescription = "Device Type Icon",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(80.dp)
                            )
                        }
                    }
                }
                item { Spacer(modifier = Modifier.padding(bottom = 60.dp)) }
            }
        }

        @Composable
        fun DeviceCard(name: String?, type: String? /*signal Strength*/) {
            Card(
                modifier = Modifier.size(width = 360.dp, height = 80.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                onClick = {/* todo new page */ }
            ) {
                Row {
                    Row(
                        Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        /*todo map signal strength to thresholds to change signal icon */
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.baseline_signal_cellular_alt_1_bar_24),
                            contentDescription = "Signal Strength",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(40.dp)
                        )
                        Column {
                            Text(
                                text = name ?: "No Name Found",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.width(192.dp),
                            )
                            Text(
                                text = type ?: "No Device Type Found",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.width(192.dp).height(20.dp),

                                )
                        }
                    }
                    /* todo logic to map type to icon*/
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.airtag_icon),
                        contentDescription = "Device Type Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(80.dp)
                    )
                }
            }
        }
}