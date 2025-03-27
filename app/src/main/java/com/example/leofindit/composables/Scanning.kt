package com.example.leofindit.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import com.example.leofindit.ui.theme.GoldPrimary
import com.example.leofindit.ui.theme.GoldPrimaryDull
import kotlinx.coroutines.delay


@Composable
fun Scanning(numberOfTrackers: Int? = 0) {
    var changeScanText by remember { mutableStateOf(false) }
    var seconds by remember { mutableIntStateOf(60) }
    LaunchedEffect(Unit) {
        while (seconds > 0) {
            delay(1000L)
            seconds--
            if ((seconds + 1) % 6 == 0) {
                changeScanText = !changeScanText
            }
        }
    }


    // text made into annotated strings to accommodate different styles
    val trackingSentence = buildAnnotatedString {
        append("We Detected")
        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
        append(" $numberOfTrackers trackers ")
        pop()
        append("around you so far")
    }
    val altText = buildAnnotatedString {
        append("A full scan can still take ")
        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
        append("$seconds" + "s ")
    }

    ScanningAnimation(size = 100f, withBackground = false)
    Text(
        text = if (!changeScanText) {
            trackingSentence
        } else {
            altText
        },
        style = MaterialTheme.typography.bodySmall,
        color = GoldPrimary
    )
    TextButton(onClick = {}) {
        Text(
            text = "Older Trackers...",
            style = MaterialTheme.typography.bodySmall,
            color = GoldPrimaryDull
        )
    }
}