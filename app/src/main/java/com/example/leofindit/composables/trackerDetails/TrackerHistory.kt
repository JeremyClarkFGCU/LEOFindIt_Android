package com.example.leofindit.composables.trackerDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.leofindit.R
import com.example.leofindit.ui.theme.LeoFindItTheme

@Composable
fun TrackerHistory (navController: NavController? = null) {
    //header
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
       // verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.size(56.dp))
        Text(
            text = "Tracker History",
            style = MaterialTheme.typography.headlineLarge,
        )
        //divider
        Box(
            modifier = Modifier
                .size(100.dp) // Circle size
                .background(
                    color = colorResource(R.color.apple_blue_light),
                    shape = CircleShape // Circle shape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.baseline_question_mark_24),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(75.dp)
            )
        }
    }
}

@Preview
@Composable
fun TrackerHistoryPreview () {
    LeoFindItTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            TrackerHistory()
        }
    }
}