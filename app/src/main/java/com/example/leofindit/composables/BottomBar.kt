package com.example.leofindit.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.leofindit.R
import com.example.leofindit.ui.theme.LeoFindItTheme

@Composable
fun BottomBar() {
    val items = listOf("Home", "Search", "Settings")
    var selectedItem by remember { mutableIntStateOf(0) }
    val selectedIcon = listOf(ImageVector.vectorResource(R.drawable.baseline_home_24), ImageVector.vectorResource(R.drawable.baseline_search_24),ImageVector.vectorResource(R.drawable.baseline_settings_24))
    var unselectedIcon = liftOf(ImageVector.vectorResource())
    NavigationBar {
        items.forEachIndexed { index, item->
        NavigationBarItem(
                icon = {
                    Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_search_24),
                    contentDescription = "Search Icon",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            onClick = {},
        )
//            IconButton(onClick = {}) {
//                Icon(
//                    imageVector = ImageVector.vectorResource(R.drawable.baseline_settings_24),
//                    contentDescription = "Settings Icon",
//                    tint = MaterialTheme.colorScheme.primary,
//                )
//            }
        }
    }
}

@Preview
@Composable
fun BottomBarPreview() {
    LeoFindItTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            BottomBar()
        }
    }
}