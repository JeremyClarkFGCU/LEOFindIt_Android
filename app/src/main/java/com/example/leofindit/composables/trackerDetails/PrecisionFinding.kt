package com.example.leofindit.composables.trackerDetails

import android.Manifest
import android.annotation.SuppressLint
import androidx.annotation.RequiresPermission
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.leofindit.R
import com.example.leofindit.composables.RoundedListItem
import com.example.leofindit.ui.theme.LeoFindItTheme
import com.example.leofindit.viewModels.BtleViewModel
import kotlin.math.floor


@SuppressLint("RestrictedApi")
@RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
@Composable
fun PrecisionFinding(navController: NavController? = null, address: String, viewModel: BtleViewModel) {
    // Create an infinite transition for animating the RSSI strength
    val infiniteTransition = rememberInfiniteTransition(label = "RSSI Animation")
    val animatedStrength by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 5000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Animated Strength"
    )
    viewModel.startScanning()
    val device = viewModel.findDevice(address)


    fun rssiNormalization(rssi: Int, minRssi : Int = -100, maxRssi : Int = -30 ) : Int {
        return((rssi - minRssi).toFloat()/ (maxRssi - minRssi) *100).toInt().coerceIn(0,100)
    }
    fun colorNormalization(){}
//    val rssiHistory = mutableListOf<Int>()

//    fun getSmoothedRssi(rssi: Int, historySize: Int = 5): Int {
//        rssiHistory.add(rssi)
//        if (rssiHistory.size > historySize) rssiHistory.removeAt(0)
//        return rssiHistory.average().toInt()  // Smooth out fluctuations
//    }
    val rawRssi = device.signalStrength
    val normRssi = rssiNormalization(rssi = rawRssi!!.toInt())

    val minColor = Color.Blue
    val maxColor = Color.Red

    // Calculate the fraction for filling (since max strength is 100)
    val fillFraction = animatedStrength / 100f
    val hotCold = Color(
        red = lerp(minColor.red, maxColor.red, fillFraction),
        green = 0f,
        blue = lerp(minColor.blue, maxColor.blue, fillFraction),
        alpha = 100f
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Animated background box: fills from the bottom up based on fillFraction
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fillFraction)
                .align(Alignment.BottomCenter)
                .background(color = hotCold)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = device.deviceName,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Column (horizontalAlignment = Alignment.CenterHorizontally){
                Text(
                    text = "${normRssi}%",
                    textAlign = TextAlign.Center,
                    fontSize = 100.sp,
                    fontWeight = FontWeight.Thin,
                    modifier = Modifier.padding(12.dp)

                )
                Text(
                    text = "Move slowly and try to maximize the signal strength to find the tracker.",
                    textAlign = TextAlign.Center
                )
            }
            Card(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .shadow(elevation = 24.dp),
                border = BorderStroke(Dp.Hairline, Color.LightGray)
            ) {
                RoundedListItem(
                    icon = ImageVector.vectorResource(R.drawable.baseline_volume_up_24),
                    color = Color(0xffff9900),
                    leadingText = "Play Sound",
                    onClick = {}

                )
                HorizontalDivider(thickness = Dp.Hairline)
                RoundedListItem(
                    icon = ImageVector.vectorResource(R.drawable.outline_close_24),
                    color = Color.Red,
                    leadingText = "Close and Stop Searching",
                    onClick = {
                        viewModel.stopScanning()
                        navController?.popBackStack()
                    }
                )
            }
        }
    }
}

@SuppressLint("RestrictedApi")
@Preview
@Composable
fun PrecisionFindingPreview() {
    LeoFindItTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            // Create an infinite transition for animating the RSSI strength
            val infiniteTransition = rememberInfiniteTransition(label = "RSSI Animation")
            val animatedStrength by infiniteTransition.animateFloat(
                initialValue = 0F,
                targetValue = 100f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 5000,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "Animated Strength"
            )

            // Calculate the fraction for filling (since max strength is 100)
            val fillFraction = animatedStrength / 100f
            val strength : Int = floor(animatedStrength).toInt()
            val minColor = Color.Blue
            val maxColor = Color.Red
            val hotCold = Color(
                red = lerp(minColor.red, maxColor.red, fillFraction),
                green = 0f,
                blue = lerp(minColor.blue, maxColor.blue, fillFraction),
                alpha = 100f
            )

            // Use a Box as the parent container to overlay the animated background and content
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Animated background box: fills from the bottom up based on fillFraction
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(fillFraction)
                        .align(Alignment.BottomCenter)
                        .background(color = hotCold)
                )

                // Overlay content: placed on top of the background
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Device Name",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Column (horizontalAlignment = Alignment.CenterHorizontally){
                        Text(
                            text = "$strength%",
                            textAlign = TextAlign.Center,
                            fontSize = 100.sp,
                            fontWeight = FontWeight.Thin,
                            modifier = Modifier.padding(12.dp)

                        )
                        Text(
                            text = "Move slowly and try to maximize the signal strength to find the tracker.",
                            textAlign = TextAlign.Center
                        )
                    }
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .shadow(elevation = 24.dp),
                        border = BorderStroke(Dp.Hairline, Color.LightGray)
                    ) {
                        RoundedListItem(
                            icon = ImageVector.vectorResource(R.drawable.baseline_volume_up_24),
                            color = Color(0xffff9900),
                            leadingText = "Play Sound",
                            onClick = {}

                        )
                        HorizontalDivider(thickness = Dp.Hairline)
                        RoundedListItem(
                            icon = ImageVector.vectorResource(R.drawable.outline_close_24),
                            color = Color.Red,
                            leadingText = "Close and Stop Searching",
                            onClick = {  }
                        )
                    }
                }
            }
        }
    }
}
