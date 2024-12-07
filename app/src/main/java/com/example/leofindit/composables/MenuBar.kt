package com.example.leofindit.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.leofindit.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MenuBar(drawerState:DrawerState, scope:CoroutineScope, content: @Composable () -> Unit) {
    fun closeDrawer() {
        scope.launch {
            drawerState.apply {
                if (isClosed) open() else close()
            }
        }
    }
    @Composable
    fun NavRow(icon: ImageVector, text:String, clickActivity: () -> Unit = {closeDrawer()}) {
        NavigationDrawerItem(
            icon = { Icon(
                imageVector = icon,
                contentDescription = "$icon Button"
            ) },
            label = { Text(text) },
            selected = false,
            onClick = { clickActivity() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Row (modifier= Modifier.padding(vertical = 18.dp, horizontal = 16.dp)){
                    TextButton(
                        onClick = { closeDrawer() },
                        shape = RectangleShape,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                            contentDescription = "Back Arrow",
                            modifier = Modifier.padding(end = 4.dp),
                            tint = Color.Black
                        )
                        Text(
                            text = "Menu",
                            modifier = Modifier.padding(start = 4.dp),
                            color = Color.Black,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
                NavRow(Icons.Filled.AccountCircle, "Accounts")
                NavRow(ImageVector.vectorResource(R.drawable.outline_folder_24), "Files")
                NavRow(Icons.Outlined.Info, "Info")
                HorizontalDivider()
                NavRow(Icons.Filled.Settings, "Settings")
            }

        },
        gesturesEnabled = false,
        content = { content() }
    )

}

