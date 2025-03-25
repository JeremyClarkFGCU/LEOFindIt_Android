// MainActivity.kt

package com.example.leofindit

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.leofindit.composables.BottomBar
import com.example.leofindit.composables.ManualScanning
import com.example.leofindit.composables.introduction.BluetoothPermission
import com.example.leofindit.composables.introduction.Introduction
import com.example.leofindit.composables.introduction.LocationAccess
import com.example.leofindit.composables.introduction.NotificationPermission
import com.example.leofindit.composables.introduction.PermissionsDone
import com.example.leofindit.composables.settings.AppInfo
import com.example.leofindit.composables.settings.Settings
import com.example.leofindit.composables.trackerDetails.ObserveTracker
import com.example.leofindit.composables.trackerDetails.PrecisionFinding
import com.example.leofindit.composables.trackerDetails.TrackerDetails
import com.example.leofindit.controller.DeviceController
import com.example.leofindit.controller.LEOPermissionHandler
import com.example.leofindit.model.BtleDevice
import com.example.leofindit.model.DeviceScanner
import com.example.leofindit.ui.theme.Background
import com.example.leofindit.ui.theme.LeoFindItTheme
import com.example.leofindit.viewModels.BtleViewModel
import kotlinx.coroutines.flow.map


const val BLUETOOTH_PERMISSIONS_REQUEST_CODE = 101
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

class MainActivity : ComponentActivity() {

    internal lateinit var deviceScanner: DeviceScanner
    private lateinit var permissionHandler: LEOPermissionHandler
    private lateinit var deviceController: DeviceController
    private val scannedDevices = mutableStateListOf<BtleDevice>()
    private var tag = "MainActivity"
    private val btleViewModel: BtleViewModel by viewModels()

    // Declare Activity Result Launcher
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    @SuppressLint("SupportAnnotationUsage", "MissingPermission")
    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN,
        [Manifest.permission.BLUETOOTH_CONNECT]
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        tag = "MainActivity.onCreate()"
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        deviceScanner = DeviceScanner(this)
        permissionHandler = LEOPermissionHandler()
        deviceController = DeviceController(deviceScanner, permissionHandler)
        enableEdgeToEdge()
        setContent {
            LeoFindItTheme {
                BtHelper.init(context = this)
                LocationHelper.locationInit(context = this)
                val mainNavController = rememberNavController()
                val introNavController = rememberNavController()
                val showBottomBar = listOf("Manual Scan", "Settings", "App info")
                val currentRoute by mainNavController.currentBackStackEntryFlow
                    .map { it.destination.route }
                    .collectAsState(initial = null)

                val context = applicationContext
                val sharedPreferences = context.getSharedPreferences("app_prefs", MODE_PRIVATE)
                var isFirstLaunch by remember {
                    mutableStateOf(sharedPreferences.getBoolean("isFirstLaunch", true))
                }


//                var topBarContent by remember {
//                    mutableStateOf<@Composable () -> Unit>({ MainTopAppBar(navController) {} })
//                }
//                val menuDrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//                val FilterDrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//                val scope = rememberCoroutineScope()
//
//                LaunchedEffect(navController) {
//                    navController.currentBackStackEntryFlow.collect { entry ->
//                        topBarContent = if (entry.destination.route == "Scan List") {
//                            {
//                                TopAppBarFilter(onMenuClick = {
//                                    scope.launch {
//                                        menuDrawerState.apply {
//                                            if (isClosed) open() else close()
//                                        }
//                                    }
//                                },
//                                    onIconClick = {
//                                        scope.launch {
//                                            FilterDrawerState.apply {
//                                                if (isClosed) open() else close()
//                                            }
//                                        }
//                                    }
//                                )
//                            }
//                        } else {
//                            {
//                                MainTopAppBar(navController, onMenuClick = {
//                                    scope.launch {
//                                        menuDrawerState.apply {
//                                            if (isClosed) open() else close()
//                                        }
//                                    }
//                                }
//                                )
//                            }
//                        }
//                    }
//                }


                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Background,
                    topBar = {},
                    bottomBar = {
                        // only shows the bottom bar during the manual scan screen
                        if(currentRoute in showBottomBar) { BottomBar(mainNavController) }
                    },
                    floatingActionButton = {
                        if(currentRoute in showBottomBar) {
                            val isScanning by btleViewModel.isScanning.collectAsState()
                            FloatingActionButton(
                            onClick = {
                                if (!btleViewModel.isScanning()) {
                                    btleViewModel.startScanning()
                                }
                                else {
                                    btleViewModel.stopScanning()
                                }
                            },
                            containerColor = Color(0xff2e2921),
                            modifier = Modifier.offset(y = (48).dp)
                        ) {
                            if (!isScanning) {
                                Icon(
                                    Icons.Filled.PlayArrow,
                                    contentDescription = "Play Button",
                                    tint = Color(0xffe9c16c)
                                )
                            } else {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.baseline_pause_24),
                                    contentDescription = "Pause Button",
                                    tint = Color.Unspecified
                                )
                            }
                        }
                    }
            },
                ) { innerPadding ->

                    if (isFirstLaunch) {
                        IntroNavigator(
                            introNavController,
                            onFinish = {
                                sharedPreferences.edit { putBoolean("isFirstLaunch", false) }
                                isFirstLaunch = false //todo change back to false
                            }
                        )
                    } else {
                        MainNavigator(
                            mainNavigator = mainNavController,
                            innerPadding = innerPadding,
                            viewModel = btleViewModel
                        )
                    }
                }
            }

        }

//        deviceScanner.setScanCallback(object : DeviceScanner.ScanCallback {
//            override fun onScanResult(devices: List<BtleDevice>) {
//                scannedDevices.clear()
//                scannedDevices.addAll(devices)
//                println("Number of scanned devices: ${scannedDevices.size}")
//            }
//        })
        deviceScanner.setScanCallback { devices ->
                scannedDevices.clear()
                scannedDevices.addAll(devices)
                println("Number of scanned devices: ${scannedDevices.size}")

        }

    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    override fun onDestroy() {
        super.onDestroy()
        deviceScanner.stopScanning()
    }
}
@Composable
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
fun IntroNavigator(introNavController: NavHostController, onFinish: () -> Unit) {
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
        composable("Bluetooth Permission")  {
            BluetoothPermission(navController = introNavController)
        }
        //Notification permission is not needed for API > 33
        composable("Notification Access") {
            NotificationPermission(navController = introNavController)
        }
        composable("Permission Done") {
            PermissionsDone(navController = introNavController,  onFinish = onFinish)
        }
    }
}



@Composable
@RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
fun MainNavigator(mainNavigator: NavHostController, innerPadding: PaddingValues, viewModel: BtleViewModel) {
    NavHost(
        navController = mainNavigator,
        startDestination = "Manual Scan"
    ) {
        composable("Manual Scan")  {
            ManualScanning(navController = mainNavigator, viewModel = viewModel)
        }
        composable ("Tracker Details/{address}", arguments = listOf(navArgument("address") {type =
            NavType.StringType}))
        { backStackEntry ->
            val  address = backStackEntry.arguments?.getString("address")!!
            TrackerDetails(navController = mainNavigator, viewModel = viewModel, address = address)
        }
        composable("Precision Finding") {
            PrecisionFinding(navController = mainNavigator)
        }
        composable("Settings") {
            Settings(navController = mainNavigator)
        }
        composable ("App info") {
            AppInfo(navController = mainNavigator)
        }
        composable("Observe Tracker") {
            ObserveTracker(navController = mainNavigator)
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