package com.example.leofindit.composables.oldUI

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import com.example.leofindit.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(navController:NavController, onMenuClick : () -> Unit) {
        CenterAlignedTopAppBar(
            title = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.leofindit_logo_master),
                    tint = Color.Unspecified,
                    contentDescription = "LeoFindIt"
                )
            },
            navigationIcon = {
                IconButton(onClick = { onMenuClick() }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu Button"
                    )
                }
            },
            actions = { // home icon
                IconButton(onClick = {navController.navigate("Scan List")} ) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Home Button"
                    )
                }
            },
        )
    HorizontalDivider()
}
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopAppBarFilter( onMenuClick: () -> Unit, onIconClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.leofindit_logo_master),
                tint = Color.Unspecified,
                contentDescription = "LeoFindIt"
            )
        },
        navigationIcon = {
            IconButton(onClick = { onMenuClick() }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu Button"
                )
            }
        },
        actions = {
            IconButton(onClick = { onIconClick() } ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.outline_filter_alt_24),
                    contentDescription = "Home Button"
                )
            }
        },
    )
}
