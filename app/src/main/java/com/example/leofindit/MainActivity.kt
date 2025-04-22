// MainActivity.kt

package com.example.leofindit

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.edit
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.leofindit.composables.ManualScanning
import com.example.leofindit.composables.introduction.BluetoothPermission
import com.example.leofindit.composables.introduction.Introduction
import com.example.leofindit.composables.introduction.LocationAccess
import com.example.leofindit.composables.introduction.NotificationPermission
import com.example.leofindit.composables.introduction.PermissionsDone
import com.example.leofindit.composables.settings.AppInfo
import com.example.leofindit.composables.settings.MarkedDevices
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
    @RequiresPermission(allOf = [Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN])
    /*********************************************************************************
     *                   Device Bt scanning vars
     *********************************************************************************/
    internal lateinit var deviceScanner: DeviceScanner
    private lateinit var permissionHandler: LEOPermissionHandler
    private lateinit var deviceController: DeviceController
    private val scannedDevices = mutableStateListOf<BtleDevice>()
    private var tag = "MainActivity"
    private val btleViewModel: BtleViewModel by viewModels()

    @SuppressLint("SupportAnnotationUsage", "MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        tag = "MainActivity.onCreate()"
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        deviceScanner = DeviceScanner(this)
        permissionHandler = LEOPermissionHandler()
        deviceController = DeviceController(deviceScanner, permissionHandler)
        enableEdgeToEdge()
        WindowCompat.getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars = false
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

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Background,
                    topBar = {},
                    bottomBar = {
                        // only shows the bottom bar during the manual scan screen
                       // if(currentRoute in showBottomBar) { BottomBar(mainNavController) }
                    },
                    floatingActionButton = {
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
                            viewModel = btleViewModel
                        )
                    }
                }
            }

        }

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
/*********************************************************************************
 *                   Main nav host, add any pages here
 *********************************************************************************/
@Composable
@RequiresPermission(allOf = [Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN])
@SuppressLint("SupportAnnotationUsage")
fun MainNavigator(mainNavigator: NavHostController, viewModel: BtleViewModel) {
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
            val  address = backStackEntry.arguments?.getString("address") ?:return@composable
            TrackerDetails(navController = mainNavigator, viewModel = viewModel, address = address)
        }
        composable("Precision Finding/{address}", arguments = listOf(navArgument("address") { type = NavType.StringType })
        ) { backStackEntry ->
            val address = backStackEntry.arguments?.getString("address") ?: return@composable
            PrecisionFinding(navController = mainNavigator, viewModel = viewModel, address = address)
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
        composable ("Marked Devices"){
            MarkedDevices(navController = mainNavigator)
        }
    }
}


/*********************************************************************************
 *                   NavHost used for introduction only
 *                   used once on first launch. Only add
 *                   for one time pages.
 *********************************************************************************/
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
