package com.example.leofindit.composables.trackerDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.leofindit.R
import com.example.leofindit.ui.theme.LeoFindItTheme

@Composable
fun MapView(geoLocation : Int? = null, ignored: Boolean? = null) {
    Card(modifier = Modifier
        .height(200.dp)
        .width(400.dp),
        onClick = {}
    ){
        /*********************************************************************************
         *                   PlaceHolder for the map component
         *                   if used add preview if device location
         *                   is available. Needs Map API key
         *********************************************************************************/
        if(geoLocation == null) {
             Column(
                 horizontalAlignment = Alignment.CenterHorizontally,
                 verticalArrangement = Arrangement.Center,
                 modifier = Modifier.fillMaxSize().padding(top = 12.dp, start = 48.dp, end = 48.dp),
                 ) {
                 Icon(
                     imageVector = ImageVector.vectorResource(R.drawable.map_fill),
                     contentDescription = null,
                     tint = colorResource(R.color.apple_blue_dark),
                     modifier = Modifier.size(32.dp)
                 )
                 Spacer(modifier = Modifier.size(16.dp))
                 Text(
                     text = if (ignored == true) {
                         "The map is unavailable because the tracker is ignored."
                     }
                     else {
                            "The map is unavailable because no locations have " +
                             "been found for this tracker"
                          },
                     textAlign = TextAlign.Center,
                     style = MaterialTheme.typography.bodyLarge,
                     fontWeight = FontWeight.Bold
                 )
             }
        }
        else {
            //todo implement actual map
        }
    }
}

@Preview
@Composable
fun MapViewPreview() {
    LeoFindItTheme {
        Surface {
            MapView()
        }
    }
}