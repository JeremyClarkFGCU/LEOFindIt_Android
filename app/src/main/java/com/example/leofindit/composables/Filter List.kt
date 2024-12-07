package com.example.leofindit.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.leofindit.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.ceil


/*
SideSheet from material 3 is not a composable component that is available so I am using a
flipped navDrawer
*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSideSheet(drawerState: DrawerState, scope: CoroutineScope, content: @Composable () -> Unit) {
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    val sliderState = SliderState(
        valueRange = 0f..100f,
        steps = 5
    )
    val interactionSource = remember { MutableInteractionSource() }
    fun closeDrawer() {
        scope.launch {
            drawerState.apply {
                if (isClosed) open() else close()
            }
        }
    }

    @Composable
    fun CheckRow(label:String ) {
        var checked by remember { mutableStateOf(false) }
        Row(verticalAlignment = Alignment.CenterVertically){
            Checkbox(
                checked = checked,
                onCheckedChange ={checked = it},
                enabled = true,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xff2e2921),
                    checkmarkColor = Color(0xffe9c16c)
                )
            )
            Text (text = label, style= MaterialTheme.typography.bodyLarge)
        }
    }
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            Column {
                                ModalDrawerSheet {
                                    Column {
                                        Row(
                                            modifier = Modifier.padding(
                                                vertical = 18.dp,
                                                horizontal = 16.dp
                                            )
                                                    .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.Start
                                        ) {
                                            TextButton(
                                                contentPadding = PaddingValues(0.dp),
                                                onClick = { closeDrawer() },
                                            ) {
                                                Icon(
                                                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                                                    contentDescription = "Back Arrow",
                                                    modifier = Modifier.padding(end = 4.dp),
                                                    tint = Color.Black
                                                )
                                                Text(
                                                    text = "Filters",
                                                    modifier = Modifier.padding(start = 4.dp),
                                                    color = Color.Black,
                                                    style = MaterialTheme.typography.titleLarge
                                                )
                                            }
                                        }
                                        Row{
                                            Text(
                                                text = "Device Type",
                                                style = MaterialTheme.typography.labelLarge,
                                                modifier = Modifier.padding(start = 12.dp)
                                            )
                                        }
                                        CheckRow( "Tags")
                                        CheckRow("Phones")
                                        CheckRow( "Computers")
                                        CheckRow("Speakers")
                                        HorizontalDivider()
                                        Slider(
                                            value = sliderPosition,
                                            onValueChange = { sliderPosition = ceil(it) },
                                            valueRange = 0f..100f,
                                            interactionSource = interactionSource,
                                            thumb = {
                                                SliderDefaults.Thumb(
                                                    interactionSource = interactionSource,
                                                )
                                            },
                                            track = {
                                                SliderDefaults.Track(sliderState = sliderState)
                                            }
                                        )
                                        Text("Signal Strength: " + sliderPosition.toInt().toString() + "%")
                                    }
                                }
                            }
                        }

                    },
                    gesturesEnabled = false,
                    content = {
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            content()
                        }

                    }
                )
            }

}

@Composable
@Preview
fun FilterPreview() {
}