package com.example.leofindit.composables.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.leofindit.R
import com.example.leofindit.composables.RoundedListItem
import com.example.leofindit.ui.theme.GoldPrimary
import com.example.leofindit.ui.theme.LeoFindItTheme

@Composable
fun Settings(navController: NavController? = null) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        //verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Spacer(modifier = Modifier.size(56.dp))


            // Title and close button row
            Box(modifier = Modifier.fillMaxWidth().padding(start = 12.dp), contentAlignment = Alignment.Center) {

                IconButton(
                    onClick = { navController?.popBackStack() },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = GoldPrimary),
                    modifier = Modifier.align(alignment = Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_arrow_back_24),
                        contentDescription = "Back Arrow",
                    )
                }
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        item {
            Spacer(modifier = Modifier.size(12.dp))

            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .shadow(elevation = 24.dp),
            ) {
                RoundedListItem(
                    onClick = { navController?.navigate("App Info") },
                    icon = ImageVector.vectorResource(R.drawable.outline_info_24),
                    color = Color.Green,
                    leadingText = "Information & Contact"
                )
                RoundedListItem(
                    onClick = { navController?.navigate("Marked Devices") },
                    icon = ImageVector.vectorResource(R.drawable.baseline_list_24) ,
                    color = Color.Gray,
                    leadingText = "Marked Device",
                )
            }
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