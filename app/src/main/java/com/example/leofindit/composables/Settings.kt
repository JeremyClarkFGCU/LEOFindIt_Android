package com.example.leofindit.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.leofindit.R
import com.example.leofindit.ui.theme.LeoFindItTheme

@Composable
fun Settings(navController: NavController? = null) {
    Column {
        Spacer(modifier = Modifier.size(56.dp))

        IconButton(
            onClick = { navController?.popBackStack() },
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.baseline_arrow_back_24),
                contentDescription = "Back Arrow"
            )
        }

        // Title and close button row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp), // Padding applied here instead
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f).padding()
            )
        }

        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .shadow(elevation = 24.dp),
            border = BorderStroke(Dp.Hairline, Color.LightGray)
        ) {
            RoundedListItem(
                onClick = { navController?.navigate("App Info") },
                icon = ImageVector.vectorResource(R.drawable.outline_info_24),
                color = Color.Green,
                leadingText = "Information & Contact"
            )
        }
    }
}


@Preview
@Composable
fun SettingsPreview() {
    LeoFindItTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Settings()
        }
    }
}