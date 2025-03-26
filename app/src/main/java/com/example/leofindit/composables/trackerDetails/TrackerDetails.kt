package com.example.leofindit.composables.trackerDetails

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.example.leofindit.R
import com.example.leofindit.composables.RoundedListItem
import com.example.leofindit.model.BtleDevice
import com.example.leofindit.ui.theme.Background
import com.example.leofindit.ui.theme.GoldPrimary
import com.example.leofindit.ui.theme.GoldPrimaryDull
import com.example.leofindit.ui.theme.LeoFindItTheme
import com.example.leofindit.ui.theme.Purple40
import com.example.leofindit.viewModels.BtleViewModel
import java.util.concurrent.TimeUnit


@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackerDetails(navController: NavController? = null, viewModel: BtleViewModel? = null, address : String) {
    val device =  viewModel!!.findDevice(address)
    var ignoreTracker by remember { mutableStateOf(false) }
   // val trackerName = trackerDetails?.deviceName ?: "Unknown"
    val timeStamp = device.timeStamp

    val timeDiffMillis = System.currentTimeMillis() - timeStamp
    val hours = TimeUnit.MILLISECONDS.toHours(timeDiffMillis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(timeDiffMillis) - TimeUnit.HOURS.toMinutes(hours)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(timeDiffMillis) - TimeUnit.MINUTES.toSeconds(minutes)

    val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
    val notCurrentlyReachable = false
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    //todo make a database of manufactures with their website to remove/disable device
    val webIntent = Intent(Intent.ACTION_VIEW,
        "https://support.thetileapp.com/hc/en-us/articles/360037001854-Disconnect-a-Partner-Device-from-My-Tile-Account#:~:text=During%20this%20process%2C%20the%20device,back%20to%20your%20Tile%20account.".toUri())

    LazyColumn (
       // modifier = Modifier.padding(horizontal = 16.dp),
    ) {
        item {
            Spacer(modifier = Modifier.size(56.dp))
            // Device name
            IconButton(onClick = { navController?.popBackStack() }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_arrow_back_24),
                    contentDescription = "Back Button",
                    tint = GoldPrimary
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = device.deviceName,
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
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
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

                //Last seen
                Text(
                    text = "Last seen: $formattedTime",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = GoldPrimaryDull,
                    modifier = Modifier.fillMaxWidth()
                )
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier.width(270.dp).align(Alignment.CenterHorizontally)
                ) {
                    val options = listOf("White List", "Black List")
                    var selectedIndex by remember { mutableIntStateOf(-1) }
                    options.forEachIndexed { index, label ->
                        val isSelected = index == selectedIndex
                        val backgroundColor = if (index == 0) Color.LightGray else Color.Black
                        val containerColor = if (index == 0) Color.Black else Color.LightGray
                        val inactiveBackgroundColor =
                            if (index == 0) Color.LightGray else Color.Black
                        val inactiveContentColor = if (index == 0) Color.Black else Color.LightGray
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = options.size
                            ),
                            onClick = {
                                selectedIndex =
                                    if (isSelected) -1
                                    else index
                                when (selectedIndex) {
                                    -1 -> {
                                        viewModel.updateDeviceState(address, false, false)
                                        Log.i("Update Device Call", "Index is: $selectedIndex Device set to neutral")
                                    }
                                    0 -> {
                                        viewModel.updateDeviceState(
                                            address = address,
                                            isSafe = true,
                                            isSuspicious = false
                                        )
                                        Log.i("Update Device Call", "Index is: $selectedIndex Device set to safe")
                                    }
                                    1 -> {
                                        viewModel.updateDeviceState(address, false, true)
                                        Log.i("Update Device Call", "Index is: $selectedIndex Device set to suspicious")
                                    }
                                }
                                Log.i("Updated Device Call", "Device: ${device.deviceAddress} is Safe: ${device.getIsSafe()} is suspicious: ${device.getIsSuspicious()}")
                            },
                            selected = index == selectedIndex,
                            enabled = true,
                            colors = SegmentedButtonDefaults.colors(
                                activeContainerColor = backgroundColor,
                                activeContentColor = containerColor,
                                inactiveContainerColor = inactiveBackgroundColor,
                                inactiveContentColor = inactiveContentColor,
                            ),
                        ) {
                            Text(label)
                        }
                    }
                }
                Card(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .shadow(elevation = 24.dp),
                ) {
                    RoundedListItem(
                        leadingText = "Device Address",
                        trailingText = device.deviceAddress ?: "Unknown",
                        trailingIcon = ImageVector.vectorResource(R.drawable.sharp_content_copy_24),
                        onClick = {
                            clipboardManager.setText(AnnotatedString(device.deviceAddress.toString()))
                                    Toast.makeText(
                                        context,
                                        "Device address copied",
                                        Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                    RoundedListItem(
                        leadingText = "Manufacturer",
                        trailingText = device.deviceManufacturer,
                        trailingIcon = ImageVector.vectorResource(R.drawable.sharp_content_copy_24),
                        onClick = {
                            clipboardManager.setText(AnnotatedString(device.deviceManufacturer.toString()))
                            Toast.makeText(
                                context,
                                "Device manufacturer copied",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                    RoundedListItem(
                        leadingText = "Device type",
                        trailingText = device.deviceType,
                        trailingIcon = ImageVector.vectorResource(R.drawable.sharp_content_copy_24),
                        onClick = {
                            clipboardManager.setText(AnnotatedString(device.deviceType.toString()))
                            Toast.makeText(
                                context,
                                "Device Type copied",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )

                }
                //map TBA
//            MapView(ignored = ignoreTracker)
//            Card(modifier = Modifier
//                .height(200.dp)
//                .width(400.dp),
//                onClick = {}
//            ){}
                // options for tracker
                Card(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .shadow(elevation = 24.dp),
                ) {
                    // list of options
                    RoundedListItem(
                        onClick = { navController?.navigate("Precision Finding") },
                        color = Color(0xff007aff),
                        icon = ImageVector.vectorResource(R.drawable.outline_explore_24),
                        leadingText = "Locate Tracker", trailingText = "Nearby"
                    )

                    HorizontalDivider(thickness = Dp.Hairline, color = Color.LightGray)

                    RoundedListItem(
                        onClick = { navController?.navigate("Observe Tracker") },
                        color = colorResource(R.color.purple_200),
                        icon = ImageVector.vectorResource(R.drawable.outline_access_time_24),
                        leadingText = "Observe Tracker", trailingText = "Off"
                    )

                    HorizontalDivider(thickness = Dp.Hairline, color = Color.LightGray)

                    RoundedListItem(
                        color = Color.Red,
                        icon = ImageVector.vectorResource(R.drawable.outline_not_interested_24),
                        leadingText = "Ignore Tracker",
                        customTrailingContent = {
                            Switch(
                                checked = ignoreTracker,
                                onCheckedChange = { isChecked -> ignoreTracker = isChecked },
                            )
                        }
                    )
                }
                //
                Text(
                    text = "Ignoring trackers will stop notifications in the background",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )

                Card(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .shadow(elevation = 16.dp),
                ) {
                    //Getting information about owner I don't think android will use this
//                RoundedListItem(
//                    onClick = {},
//                    icon = ImageVector.vectorResource(R.drawable.outline_person_24),
//                    color = colorResource(R.color.Orange),
//                    leadingText = "Information About Owner",
//                )
                    RoundedListItem(
                        onClick = { context.startActivity(webIntent) },
                        icon = ImageVector.vectorResource(R.drawable.outline_info_24),
                        color = Color.Green,
                        leadingText = "Manufacture's Website",
                        trailingIcon = ImageVector.vectorResource(R.drawable.baseline_link_24),
                        iconModifier = Modifier.rotate(-45F)
                    )
                }

                Text(
                    text = " Learn more, e.g how to disable the tracker",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}



@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TrackerDetailsPreview() {
    val sampleBtleDevice = BtleDevice(
        deviceName = "Airpods",
        deviceManufacturer = "Apple",
        deviceAddress = "000.000.000",
        deviceType = "Headphones",
        timeStamp = 0,
        signalStrength = -100,
        isSuspicious = false,
        isTag = false,
        isSafe = true,
        isParent = false,
        isTarget = false,
        nickName = null
    )
    LeoFindItTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Background) {
            val device = sampleBtleDevice
            var ignoreTracker by remember { mutableStateOf(false) }
            // val trackerName = trackerDetails?.deviceName ?: "Unknown"
            val timeStamp = device.timeStamp

            val timeDiffMillis = System.currentTimeMillis() - timeStamp
            val hours = TimeUnit.MILLISECONDS.toHours(timeDiffMillis)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(timeDiffMillis) - TimeUnit.HOURS.toMinutes(hours)
            val seconds = TimeUnit.MILLISECONDS.toSeconds(timeDiffMillis) - TimeUnit.MINUTES.toSeconds(minutes)

            val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            val notCurrentlyReachable = false
            val context = LocalContext.current
            val clipboardManager = LocalClipboardManager.current
            //todo make a database of manufactures with their website to remove/disable device
            val webIntent = Intent(
                Intent.ACTION_VIEW,
                "https://support.thetileapp.com/hc/en-us/articles/360037001854-Disconnect-a-Partner-Device-from-My-Tile-Account#:~:text=During%20this%20process%2C%20the%20device,back%20to%20your%20Tile%20account.".toUri()
            )


            LazyColumn (
                // modifier = Modifier.padding(horizontal = 16.dp),
            ) {
                item {
                Spacer(modifier = Modifier.size(56.dp))
                // Device name
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_arrow_back_24),
                        contentDescription = "Back Button",
                        tint = GoldPrimary

                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = device.deviceName,
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
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
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

                    //Last seen
                    Text(
                        text = "Last seen: ${formattedTime}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = GoldPrimaryDull,
                        modifier = Modifier.fillMaxWidth()
                    )
                    SingleChoiceSegmentedButtonRow(
                        modifier = Modifier.width(270.dp).align(Alignment.CenterHorizontally)
                    ) {
                        val options = listOf("White List", "Black List")
                        var selectedIndex by remember { mutableIntStateOf(-1) }
                        options.forEachIndexed { index, label ->
                            val isSelected = index == selectedIndex
                            val backgroundColor = if (index == 0) Color.White else Color.Black
                            val containerColor = if (index == 0) Color.Black else Color.White
                            val inactiveBackgroundColor =
                                if (index == 0) Color.White else Color.Black
                            val inactiveContentColor = if (index == 0) Color.Black else Color.White
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = options.size
                                ),
                                onClick = { selectedIndex = if (isSelected) -1 else index
                                          Log.i("index", "index is: $selectedIndex")},
                                selected = index == selectedIndex,
                                enabled = true,
                                colors = SegmentedButtonDefaults.colors(
                                    activeContainerColor = backgroundColor,
                                    activeContentColor = containerColor,
                                    inactiveContainerColor = inactiveBackgroundColor,
                                    inactiveContentColor = inactiveContentColor,
                                ),
                            ) {
                                Text(label)
                            }
                        }
                    }
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .shadow(elevation = 24.dp),
                    ) {
                        RoundedListItem(
                            leadingText = "Device Address",
                            trailingText = device.deviceAddress ?: "Unknown",
                            trailingIcon = ImageVector.vectorResource(R.drawable.sharp_content_copy_24),
                            onClick = {
                                clipboardManager.setText(AnnotatedString(device.deviceAddress.toString()))
                                Toast.makeText(
                                    context,
                                    "Device address copied",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                        RoundedListItem(
                            leadingText = "Manufacturer",
                            trailingText = device.deviceManufacturer,
                            trailingIcon = ImageVector.vectorResource(R.drawable.sharp_content_copy_24),
                            onClick = {
                                clipboardManager.setText(AnnotatedString(device.deviceManufacturer.toString()))
                                Toast.makeText(
                                    context,
                                    "Device manufacturer copied",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                        RoundedListItem(
                            leadingText = "Device type",
                            trailingText = device.deviceType,
                            trailingIcon = ImageVector.vectorResource(R.drawable.sharp_content_copy_24),
                            onClick = {
                                clipboardManager.setText(AnnotatedString(device.deviceType.toString()))
                                Toast.makeText(
                                    context,
                                    "Device Type copied",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    }
                    //map TBA
//            MapView(ignored = ignoreTracker)
//            Card(modifier = Modifier
//                .height(200.dp)
//                .width(400.dp),
//                onClick = {}
//            ){}
                    // options for tracker
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .shadow(elevation = 24.dp),
                    ) {
                        // list of options
                        RoundedListItem(
                            onClick = { },
                            color = Color(0xff007aff),
                            icon = ImageVector.vectorResource(R.drawable.outline_explore_24),
                            leadingText = "Locate Tracker", trailingText = "Nearby"
                        )

                        HorizontalDivider(thickness = Dp.Hairline, color = Color.LightGray)

                        RoundedListItem(
                            onClick = { },
                            color = Purple40,
                            icon = ImageVector.vectorResource(R.drawable.outline_access_time_24),
                            leadingText = "Observe Tracker", trailingText = "Off"
                        )

                        HorizontalDivider(thickness = Dp.Hairline, color = Color.LightGray)

                        RoundedListItem(
                            color = Color.Red,
                            icon = ImageVector.vectorResource(R.drawable.outline_not_interested_24),
                            leadingText = "Ignore Tracker",
                            customTrailingContent = {
                                Switch(
                                    checked = ignoreTracker,
                                    onCheckedChange = { isChecked -> ignoreTracker = isChecked },
                                )
                            }
                        )
                    }
                    //
                    Text(
                        text = "Ignoring trackers will stop notifications in the background",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )

                    Card(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .shadow(elevation = 16.dp),
                    ) {
                        //Getting information about owner I don't think android will use this
//                RoundedListItem(
//                    onClick = {},
//                    icon = ImageVector.vectorResource(R.drawable.outline_person_24),
//                    color = colorResource(R.color.Orange),
//                    leadingText = "Information About Owner",
//                )
                        RoundedListItem(
                            onClick = { context.startActivity(webIntent) },
                            icon = ImageVector.vectorResource(R.drawable.outline_info_24),
                            color = Color.Green,
                            leadingText = "Manufacture's Website",
                            trailingIcon = ImageVector.vectorResource(R.drawable.baseline_link_24),
                            iconModifier = Modifier.rotate(-45F)
                        )
                    }

                    Text(
                        text = " Learn more, e.g how to disable the tracker",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}
}