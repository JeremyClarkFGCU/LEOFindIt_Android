package com.example.leofindit.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.ceil


/*
SideSheet from material 3 is not a composable component that is available so I am using a
flipped navDrawer
*/
@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSideSheet(drawerState: DrawerState, scope: CoroutineScope, content: @Composable () -> Unit) {
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    val filterList = linkedMapOf(
        "Tags" to false,
        "Phones" to false,
        "Computers" to false,
        "Speakers" to false
    )
    val filters = remember { mutableStateMapOf<String, Boolean>() }
    filters.putAll(filterList)
    // todo confirm the range and units we will use for the slider
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

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            Column {
                                //actual Filter side sheet content
                                ModalDrawerSheet{
                                    Column (modifier = Modifier.padding(start = 24.dp, end = 24.dp)){
                                        Row(
                                            modifier = Modifier.padding(
                                                vertical = 18.dp,
                                            )
                                                    .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.Start
                                        ) {
                                            TextButton(
                                                contentPadding = PaddingValues(0.dp),
                                                onClick = { closeDrawer() },
                                            ) {
                                                Icon(
                                                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                                    contentDescription = "Back Arrow",
                                                    modifier = Modifier.padding(end = 12.dp),
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

                                            )
                                        }
                                        filterList.forEach { (filterName, _) ->
                                            val isChecked = filters[filterName] ?: false
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Checkbox(
                                                    enabled = true,
                                                    checked = isChecked,
                                                    onCheckedChange = { newCheckedState ->
                                                        filters[filterName] = newCheckedState
                                                    },
                                                    colors = CheckboxDefaults.colors(
                                                            checkedColor = Color(0xff2e2921),
                                                        checkmarkColor = Color(0xffe9c16c)
                                                    )
                                                )
                                                Text(filterName)
                                            }
                                        }
                                        HorizontalDivider()
                                        Slider(
                                            value = sliderPosition,
                                            onValueChange = { sliderPosition = ceil(it) },
                                            valueRange = 0f..100f,
                                            interactionSource = interactionSource,
                                            colors = SliderDefaults.colors(inactiveTickColor = Color(0xffe9c16c), activeTrackColor = Color(0xffffffff)),
                                            thumb = {
                                                SliderDefaults.Thumb(
                                                    interactionSource = interactionSource,
                                                    colors = SliderDefaults.colors(thumbColor = Color(0xff2e2921) )
                                                )
                                            },
                                            track = {
                                                SliderDefaults.Track(sliderState = sliderState)
                                            },
                                            modifier = Modifier.padding(16.dp)
                                        )
                                        Text("Signal Strength: " + sliderPosition.toInt().toString() + "%")
                                        Spacer(modifier = Modifier.weight(1f))
                                        HorizontalDivider(thickness = (2 * DividerDefaults.Thickness))
                                        Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 24.dp)) {
                                            OutlinedButton(onClick = {filters.forEach{ (filterName, _) ->
                                                filters[filterName] = false
                                            }},
                                                modifier = Modifier.padding(end = 16.dp),
                                                colors = ButtonDefaults.filledTonalButtonColors()){
                                                Text("Clear")
                                            }
                                        }
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