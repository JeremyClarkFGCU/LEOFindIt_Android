package com.example.leofindit.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//todo pass device class instead of name uuid ...etc
fun DeviceDetails(modifier: Modifier = Modifier, navController: NavController, deviceName:String?, deviceUUID:String?) {
    var selectedIndex by remember { mutableIntStateOf(-1) }
    val options = listOf("White List", "Black List")
    Scaffold(
        modifier = modifier,
        topBar = { MainTopAppBar(navController) {} },
    ) { innerPadding ->
        Column (
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    brush = Brush.verticalGradient(
                    colors = listOf(Color(0xffdebb00), Color(0xff000000)))
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ){
            SingleChoiceSegmentedButtonRow(
                modifier = modifier.width(270.dp)
            ) {
                options.forEachIndexed{index, label ->
                    val isSelected = index == selectedIndex
                    val backgroundColor = if(index==0) Color.White else Color.Black
                    val containerColor = if (index==0) Color.Black else Color.White
                    val inactiveBackgroundColor = if (index == 0) Color.White else Color.Black
                    val inactiveContentColor = if (index == 0) Color.Black else Color.White
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                        onClick = {selectedIndex = if (isSelected) -1 else index},
                        selected = index == selectedIndex,
                        enabled = true,
                        colors = SegmentedButtonColors(
                            activeContainerColor = backgroundColor,
                            activeContentColor = containerColor,
                            inactiveContainerColor = inactiveBackgroundColor,
                            inactiveContentColor = inactiveContentColor,
                            activeBorderColor = Color.Black,
                            inactiveBorderColor = Color.Black,
                            disabledActiveBorderColor = Color.Black,
                            disabledActiveContainerColor = Color.Black,
                            disabledActiveContentColor = Color.Black,
                            disabledInactiveBorderColor = Color.Black,
                            disabledInactiveContainerColor = Color.Black,
                            disabledInactiveContentColor = Color.Black,
                        ),
                    ) {
                        Text(label)
                    }
                }
            }
            Spacer(modifier = modifier.size(72.dp))
//            Icon(imageVector = ImageVector.vectorResource(R.drawable.radar_svgrepo_com),
//                contentDescription = "radar", tint = Color.Unspecified,
//            )
            Box(
                modifier = modifier
                    .size(300.dp)
                    .background(
                        color= Color(0xff009900),
                        shape = CircleShape,

                    )
            )
            Spacer(modifier = modifier.size(32.dp))
            Card(modifier = modifier
                .width(350.dp)
                .height(215.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xff49454f)
                )

            ) {
                LazyColumn(modifier= modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
                    item {
                        Row(
                            modifier = modifier
                                .height(24.dp),
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = "Info Icon"
                            )
                            Text(
                                text = deviceName ?: deviceUUID ?: "null",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = modifier.width(232.dp)
                            )
                        }
                    }
                    item {
                        Text(
                            text = "UUID:${deviceUUID}\nDetail 1 \nDetail 2\nDetail3\n",
                            color = Color.White,
                            lineHeight = 24.sp
                        )

                    }
                }
            }
        }
    }
}

