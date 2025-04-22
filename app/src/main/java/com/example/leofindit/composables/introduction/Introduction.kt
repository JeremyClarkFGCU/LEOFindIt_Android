//
//  Introduction.kt
//  LeoFindIt
//
//  Written by Brian Zapata Resendiz

// IntroductionView.kt
package com.example.leofindit.composables.introduction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.leofindit.R
import com.example.leofindit.ui.theme.GoldPrimary
import com.example.leofindit.ui.theme.GoldPrimaryDull
import com.example.leofindit.ui.theme.LeoFindItTheme
import com.example.leofindit.ui.theme.OnSurface
import com.example.leofindit.ui.theme.Surface

/*********************************************************************************
 *                   Welcome page for app (once only)
 *********************************************************************************/
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
                color = Surface
            )
            Text(
                text = "Proximity Tracker",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = GoldPrimary,
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
                    tint = GoldPrimaryDull,
                    modifier = Modifier.size(40.dp),
                )
                Column {
                    Text(
                        text = "Manual Scan",
                        fontWeight = FontWeight.Bold,
                        color = GoldPrimary,
                    )
                    Text(
                        color = GoldPrimary,
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
                    tint = GoldPrimaryDull,
                    modifier = Modifier.size(40.dp)
                )
                Column {
                    Text(
                        text = "We respect your data",
                        fontWeight = FontWeight.Bold,
                        color = GoldPrimary,
                    )
                    Text(
                        text = "Developed by Florida Gulf Coast University students without commercial interests.",
                        fontWeight = FontWeight.Light,
                        color = GoldPrimary,

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
            colors = ButtonDefaults.buttonColors(containerColor = Surface, contentColor = OnSurface)

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