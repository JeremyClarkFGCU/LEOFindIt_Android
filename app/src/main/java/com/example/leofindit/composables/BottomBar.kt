package com.example.leofindit.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
    val items = listOf( "Search", "Home", "Settings")
    var selectedItem by remember { mutableIntStateOf(0) }
    val selectedIcon = listOf(
        ImageVector.vectorResource(R.drawable.baseline_search_24),
        ImageVector.vectorResource(R.drawable.baseline_home_24),
        ImageVector.vectorResource(R.drawable.baseline_settings_24)
    )
    var unselectedIcon = listOf(
        ImageVector.vectorResource(R.drawable.outline_search_24),
        ImageVector.vectorResource(R.drawable.outline_home_24),
        ImageVector.vectorResource(R.drawable.outline_settings_24)
    )
    NavigationBar (modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background)) {
        items.forEachIndexed { index, item->
        NavigationBarItem(
                icon = {
                    Icon(
                    if (selectedItem == index) selectedIcon[index] else unselectedIcon[index],
                    contentDescription = item
                )
            },
            label = {Text(text = items[index])},
            onClick = {selectedItem = index },
            selected = selectedItem == index,
            alwaysShowLabel = false
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
        Surface() {
            BottomBar()
        }
    }
}