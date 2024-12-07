package com.example.leofindit.composables

import android.annotation.SuppressLint
import android.net.http.HeaderBlock
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StartScan(modifier: Modifier = Modifier, navController: NavController) {
    Scaffold(
        modifier = modifier,
        //topBar = { MainTopAppBar(navController) }
    )
    {

        Column (
            modifier = modifier
                .padding(bottom = 0.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = modifier
                    .size(300.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xffffd700), Color(0xff000000))
                        ),
                        shape = CircleShape
                    ),
            ) {

                Button(
                    onClick = { navController.navigate("Scan List") },
                    modifier.size(300.dp),
                    shape = CircleShape,
                    colors = buttonColors(containerColor = Color.Transparent)
                ) {
                    Text(
                        text = "Start Scan",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White
                    )
                }
            }
        }
    }
}
