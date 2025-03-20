package com.example.leofindit.view

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import com.example.leofindit.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun appTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                "LEO FindIt",
                style = MaterialTheme.typography.headlineSmall,
                color = OnPrimary
            )
        },
        navigationIcon = {
            IconButton(onClick = { /*TODO: Handle navigation icon click*/ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu",
                    tint = OnPrimary
                )
            }
        },
        actions = {
            IconButton(onClick = { /*TODO: Handle person icon click*/ }) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Profile",
                    tint = OnPrimary
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = GoldPrimary
        )
    )
}