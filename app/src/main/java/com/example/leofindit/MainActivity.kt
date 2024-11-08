package com.example.leofindit

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.leofindit.composables.DeviceDetails
import com.example.leofindit.composables.MainTopAppBar
import com.example.leofindit.composables.MenuBar
import com.example.leofindit.composables.ScanList
import com.example.leofindit.composables.StartScan
import com.example.leofindit.composables.TopAppBarFilter
import com.example.leofindit.ui.theme.LeoFindItTheme
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LeoFindItTheme {
                val navController = rememberNavController()
                var topBarContent by remember {
                    mutableStateOf<@Composable () -> Unit>({MainTopAppBar(navController){} })
                }
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                LaunchedEffect(navController) {
                    navController.currentBackStackEntryFlow.collect { entry ->
                        topBarContent = if (entry.destination.route == "Scan List") {
                            {
                                TopAppBarFilter( onIconClick =
                                {
                                    scope.launch {
                                        drawerState.apply {
                                            if (isClosed) open() else close()
                                        }
                                    }
                                })
                            }
                        }
                        else {
                            {
                                MainTopAppBar(
                                    navController,
                                    onMenuClick = {
                                        scope.launch {
                                            drawerState.apply {
                                                if (isClosed) open() else close()
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
                MenuBar(drawerState = drawerState, scope = scope,) {
                    Scaffold(
                        topBar = topBarContent

                    ) { innerPadding ->
                        val modifier = Modifier.padding(innerPadding)
                        NavHost(
                            navController = navController,
                            startDestination = "Start Scan"
                        ) {
                            composable("Start Scan") {
                                StartScan(navController = navController)
                            }
                            composable("Scan List") {
                                ScanList(navController = navController)
                            }
                            composable(
                                route = "Device Details/{name}/{uuid}",
                                arguments = listOf(
                                    navArgument("name") { type = NavType.StringType },
                                    navArgument("uuid") { type = NavType.StringType },
                                )
                            ) { backStackEntry ->
                                val name = backStackEntry.arguments?.getString("name")
                                val uuid = backStackEntry.arguments?.getString("uuid")
                                DeviceDetails(
                                    navController = navController,
                                    deviceName = name,
                                    deviceUUID = uuid
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
