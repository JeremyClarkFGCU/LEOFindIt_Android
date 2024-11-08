package com.example.leofindit.composables

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    fun NavRow(icon: ImageVector, text:String) {
        NavigationDrawerItem(
            icon = { Icon(
                imageVector = icon,
                contentDescription = "$icon Button"
            ) },
            label = { Text(text) },
            selected = false,
            onClick = { closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Menu",
                    modifier = Modifier
                        .padding(vertical = 18.dp, horizontal = 16.dp)
                )
                NavRow(Icons.Filled.AccountCircle, "Accounts")
                NavRow(ImageVector.vectorResource(R.drawable.outline_folder_24), "Files")
                NavRow(Icons.Outlined.Info, "Info")
                HorizontalDivider()
                NavRow(Icons.Filled.Settings, "settings")
            }

        },
        content = { content() }
    )

}

