//
//  ManualScanning.kt
//  LeoFindIt
//
//  Created by Brian Zapata Resendiz
package com.example.leofindit.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.leofindit.ui.theme.LeoFindItTheme
 // change text in 6 seconds
@Composable
fun ManualScanning(navController : NavController? = null) {
    val numberOfTrackers : Int? = 0
    val trackingSentence = buildAnnotatedString {
        append("We Detected")
        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
        append(" $numberOfTrackers trackers ")
        pop()
        append("around you so far")
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(Modifier.size(56.dp))
        Text(
            text = "Scan",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start).padding(start = 8.dp)
        )
        ScanningAnimation(size = 100f, withBackground = false)
        Text(
            text = trackingSentence,
            style = MaterialTheme.typography.bodySmall
            )
        TextButton(onClick = {}) {
         Text(
            text = "Older Trackers",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
         )
        }

    }
}
@Preview
@Composable
fun ManualScanningPreview() {
    LeoFindItTheme {
        Surface(modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ManualScanning()
        }
    }
}
