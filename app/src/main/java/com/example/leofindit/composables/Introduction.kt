//
//  Introduction.kt
//  LeoFindIt
//
//  Written by Brian Zapata Resendiz

// IntroductionView.kt
package com.example.leofindit.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.leofindit.R
import com.example.leofindit.ui.theme.LeoFindItTheme


@Composable
fun Introduction(navController: NavController? = null) {
    val primaryColor = MaterialTheme.colorScheme.primary



    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Title
            Text(
                text = "Welcome to",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
            )
            Text(
                text = "Proximity Tracker",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = primaryColor,
                //color = Color(0xff648de2),
                fontSize = 32.sp,
                )
        }
        //App Description
        Column {
            Row (
                horizontalArrangement = Arrangement.spacedBy(space = 16.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom= 32.dp, start = 12.dp)
            ){
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_search_24),
                    contentDescription = "Manual Scan",
                    tint = primaryColor,
                    modifier = Modifier.size(40.dp)
                )
                Column {
                    Text(
                        text = "Manual Scan",
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "Scan for surrounding AirTags, SmartTags, Tiles and more!",
                        fontWeight = FontWeight.Light
                    )
                }
            }
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = 16.dp, Alignment.Start),
                modifier = Modifier.padding(start = 12.dp)

            ){
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_lock_24),
                    contentDescription = "Manual Scan",
                    tint = primaryColor,
                    modifier = Modifier.size(40.dp)
                )
                Column {
                    Text(
                        text = "We respect your data",
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "Developed by Florida Gulf Coast University students without commercial interests.",
                        fontWeight = FontWeight.Light

                    )
                }
            }
        }
        //Button todo add navigation
        Button(
            onClick = {
                navController?.navigate("Location Permission")
            },
            modifier = Modifier.fillMaxWidth(.75f),

            )
        {
            Text("Continue")
        }
    }

}

//@Composable
//fun IntroductionButtonView(
//    controller: IntroductionViewController,
//    action: () -> Unit,
//    nextView: @Composable () -> Unit
//) {
//    var linkActive by remember { mutableStateOf(false) }
//
//    ColoredButton(
//        onClick = { },
//        label = "continue"
//    )
//
//    if (linkActive) {
//        nextView()
//    }
//}
//
//@Composable
//fun TitleView(title: String) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Text(
//            text = "R.string.welcome_to", // Replace with actual string resource
//            style = TextStyle.Default,
//            modifier = Modifier.padding(top = 16.dp)
//        )
//
//        Text(
//            text = "Proximity Tracker",
//            style = TextStyle.Default,
//            modifier = Modifier.padding(top = 8.dp),
////            color = Brush.linearGradient(Constants.defaultColors)
//        )
//    }
//}
//
//@Composable
//fun InformationContainerView(modifier: Modifier = Modifier) {
//    Column(
//        modifier = modifier.fillMaxWidth(),
//        verticalArrangement = Arrangement.spacedBy(20.dp)
//    ) {
////        InformationDetailView(
////            title = "manual_scan",
////            subTitle = "manual_scan_description",
////            imageName = R.drawable.ic_magnifying_glass // Replace with actual drawable
////        )
//
////        InformationDetailView(
////            title = "respect_data",
////            subTitle = "respect_data_description",
////            imageName = R.drawable.ic_lock // Replace with actual drawable
////        )
//    }
//}
//
//@Composable
//fun InformationDetailView(title: String, subTitle: String, imageName: Int) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = 8.dp, end = 8.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Icon(
//            painter = painterResource(id = imageName),
//            contentDescription = null,
//            modifier = Modifier
//                .size(50.dp)
//                .padding(end = 8.dp),
//            //tint = Brush.linearGradient(Constants.defaultColors)
//        )
//
//        Column {
//            Text(
//                text = "dddddddddd", // Replace with string resource
//                style = MaterialTheme.typography.headlineSmall
//            )
//
//            Text(
//                text = "stringResource(id = R.string.sub_title)", // Replace with string resource
//                style = MaterialTheme.typography.bodyMedium
//            )
//        }
//    }
//}
//
//@Composable
//fun BluetoothPermissionView(navController: NavController) {
//    // Implementation of Bluetooth permission logic
//}
//
//@Composable
//fun ColoredButton(onClick: () -> Unit, label: String) {
//    Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
//        Text(text = label)
//    }
//}
//
//// Singleton ViewModel equivalent
//class IntroductionViewController private constructor() {
//
//    companion object
//}


@Preview
@Composable
fun IntroPreview() {
    LeoFindItTheme {
        Surface(modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Introduction()
        }
    }
}