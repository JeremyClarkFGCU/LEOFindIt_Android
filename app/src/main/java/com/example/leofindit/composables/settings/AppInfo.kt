package com.example.leofindit.composables.settings

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.example.leofindit.R
import com.example.leofindit.composables.RoundedListItem
import com.example.leofindit.ui.theme.GoldPrimary
import com.example.leofindit.ui.theme.LeoFindItTheme

@SuppressLint("QueryPermissionsNeeded")
@Composable
fun AppInfo(navController: NavController? = null) {
    val context = LocalContext.current
    val emailAddress = "leofindit@gmail.com" //example
    val subject = "Feedback; Version 1.0.0"
    val mailIntent = Intent(
        Intent.ACTION_SENDTO,
        "mailto:$emailAddress?subject=${Uri.encode(subject)}".toUri()
    )
    val brianGitHub = Intent(Intent.ACTION_VIEW, "https://github.com/BzapataR".toUri())
    val jeremyGitHub = Intent(Intent.ACTION_VIEW, "https://github.com/JeremyClarkFGCU".toUri())

    Column {
        Spacer(modifier = Modifier.size(56.dp))
        IconButton(
            colors = IconButtonDefaults.iconButtonColors(contentColor = GoldPrimary),
            onClick = { navController?.popBackStack() }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.baseline_arrow_back_24),
                contentDescription = null,
                )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                color = GoldPrimary,
                text = "Proximity Tracker for Android",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
            )
            Text(
                color = GoldPrimary,
                text = "Version 1.0.0",
                style = MaterialTheme.typography.bodySmall,
            )

        }
        Card(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .shadow(elevation = 24.dp),
        ) {
            RoundedListItem(
                onClick = {
                    context.startActivity(
                        Intent.createChooser(mailIntent, "Choose Email Client")
                    )
                },
                icon = ImageVector.vectorResource(R.drawable.baseline_mail_24),
                color = colorResource(R.color.apple_blue_light),
                leadingText = "Send us Feedback",
                trailingIcon = ImageVector.vectorResource(R.drawable.baseline_link_24),
                iconModifier = Modifier.rotate(-45F)
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        Card(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .shadow(elevation = 24.dp),
        ) {
            RoundedListItem(
                onClick = { context.startActivity(brianGitHub) },
                icon = ImageVector.vectorResource(R.drawable.baseline_brush_24),
                color = Color.Green,
                leadingText = "UI Developer", trailingText = "Brian Zapata",
                trailingIcon = ImageVector.vectorResource(R.drawable.baseline_link_24),
                iconModifier = Modifier.rotate(-45F)
            )
            RoundedListItem(
                onClick = { context.startActivity(jeremyGitHub) },
                icon = ImageVector.vectorResource(R.drawable.outline_data_object_24),
                color = colorResource(R.color.Orange),
                leadingText = "Bluetooth Backend", trailingText = "Jeremy Clark",
                trailingIcon = ImageVector.vectorResource(R.drawable.baseline_link_24),
                iconModifier = Modifier.rotate(-45F)
            )
        }
    }
}

@Preview
@Composable
fun AppInfoPreview() {
    LeoFindItTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AppInfo()
        }
    }

}