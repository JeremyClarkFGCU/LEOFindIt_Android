package com.example.leofindit

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import com.example.leofindit.composables.BluetoothPermission
import com.example.leofindit.composables.DeviceDetails
import com.example.leofindit.composables.FilterSideSheet
import com.example.leofindit.composables.Introduction
import com.example.leofindit.composables.IntroductionMainView
import com.example.leofindit.composables.LocationAccess
import com.example.leofindit.composables.MainTopAppBar
import com.example.leofindit.composables.MenuBar
import com.example.leofindit.composables.NotificationPermission
import com.example.leofindit.composables.PermissionsDone
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
                    mutableStateOf<@Composable () -> Unit>({ MainTopAppBar(navController) {} })
                }
                val menuDrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val FilterDrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                LaunchedEffect(navController) {
                    navController.currentBackStackEntryFlow.collect { entry ->
                        topBarContent = if (entry.destination.route == "Scan List") {
                            {
                                TopAppBarFilter(onMenuClick = {
                                    scope.launch {
                                        menuDrawerState.apply {
                                            if (isClosed) open() else close()
                                        }
                                    }
                                },
                                    onIconClick = {
                                        scope.launch {
                                            FilterDrawerState.apply {
                                                if (isClosed) open() else close()
                                            }
                                        }
                                    }
                                )
                            }
                        } else {
                            {
                                MainTopAppBar(navController, onMenuClick = {
                                    scope.launch {
                                        menuDrawerState.apply {
                                            if (isClosed) open() else close()
                                        }
                                    }
                                }
                                )
                            }
                        }
                    }
                }
                Surface {
                    val introNavController = rememberNavController()
                    NavHost(
                        navController = introNavController,
                        startDestination = "Introduction"
                    ) {
                        composable("Introduction") {
                            Introduction(navController = introNavController)
                            //FilterSideSheet()
                        }
                        composable("Location Permission") {
                            LocationAccess(navController = introNavController)
                        }
                        composable("Bluetooth Permission") {
                            BluetoothPermission(navController = introNavController)
                        }
                        composable("Notification Access") {
                            NotificationPermission(navController = introNavController)
                        }
                        composable("Permission Done") {
                            PermissionsDone(navController = introNavController)
                        }
                    }
                }

            }
        }
    }
}
/*
                MenuBar(drawerState = menuDrawerState, scope = scope,) {
                    FilterSideSheet(drawerState = FilterDrawerState, scope = scope) {
                        Scaffold(topBar = topBarContent) { innerPadding ->
                            val modifier = Modifier.padding(innerPadding)
                            val color = MaterialTheme.colorScheme.background

                            NavHost(
                                navController = navController,
                                startDestination = "Start Scan"
                            ) {
                                composable("Start Scan") {
                                    StartScan(navController = navController)
                                    //FilterSideSheet()
                                }
                                composable("Scan List") {
                                    ScanList(modifier = modifier, navController = navController)
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
}
*/