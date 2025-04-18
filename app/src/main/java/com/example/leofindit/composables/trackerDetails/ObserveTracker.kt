package com.example.leofindit.composables.trackerDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.leofindit.R
import com.example.leofindit.ui.theme.LeoFindItTheme

@Composable
fun ObserveTracker(navController: NavController? = null) {
    //********************************************************************************
    //                    Not Used add map here?
    //********************************************************************************
    Column {
        Spacer(modifier = Modifier.size(56.dp))
        IconButton(onClick = { navController?.popBackStack() } ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.baseline_arrow_back_24),
                contentDescription = "Back Arrow",
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Observe Tracker",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
            )
            Spacer(modifier = Modifier.size(56.dp))
            Box(
                modifier = Modifier.background(
                    shape = CircleShape,
                    color = colorResource(R.color.apple_blue_light)
                ).size(100.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.outline_access_time_24),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(100.dp)
                )
            }
            Spacer(modifier = Modifier.size(56.dp))
            Text(
                text = "If you are not sure if this tracker follow you, you can manually observe" +
                        " it.",
                textAlign = TextAlign.Center
            )
            Text(
                text="In one hour, Proximity Tracker will search for this tracker and deliver a " +
                        "notification immediately, even if you did not move with this tracker. If" +
                        " the tracker is not around anymore, you will receive a notification " +
                        "about that as well",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(56.dp))
            Button(onClick = {}, modifier = Modifier.fillMaxWidth(.75F)) {
                Text(
                    text = "Start Observation",
                )

            }
        }
    }
}

@Preview
@Composable
fun ObserveTrackerPreview() {
    LeoFindItTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ObserveTracker()
        }
    }
}