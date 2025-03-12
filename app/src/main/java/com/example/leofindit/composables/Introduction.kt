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