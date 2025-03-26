package com.example.leofindit.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.leofindit.ui.theme.Background
import com.example.leofindit.ui.theme.GoldPrimaryDull
import com.example.leofindit.ui.theme.Surface

@Composable
fun RoundedListItem(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    icon: ImageVector? = null,
    color: Color = Color.Unspecified,
    leadingText: String,
    trailingText: String = "",
    trailingIcon: ImageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
    customTrailingContent : @Composable (() -> Unit)? = null,
    iconModifier: Modifier = Modifier,
) {
    fun shrinkText(string: String) : String {
        return if(string.length >= 26) {
            string.take(26) + "..."
        } else
            string
    }
    ListItem(
        colors = ListItemDefaults.colors(containerColor = Surface,),
        leadingContent = {
            if (icon != null) {
                Box(
                    modifier = modifier
                        .size(24.dp) // Circle size
                        .background(
                            color = color,
                            shape = CircleShape // Circle shape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = Background,
                            modifier = modifier.size(20.dp)
                        )
                    }
            }
        },
        headlineContent = {
            Row(modifier= Modifier) {
                Text(
                    text = leadingText,
                    color = GoldPrimaryDull,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
            }
                          },
        trailingContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                ) {
                if (customTrailingContent != null) {
                    customTrailingContent()
                } else {
                    Text(
                        text = shrinkText(trailingText),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(12f,false).padding(end = 4.dp),
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,

                    )
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = iconModifier
                    )
                }
            }
        },
        modifier = if (onClick != null) Modifier.clickable{ onClick() } else Modifier
    )
}
