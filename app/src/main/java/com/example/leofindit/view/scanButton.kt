package com.example.leofindit.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.leofindit.ui.theme.GoldPrimary
import com.example.leofindit.ui.theme.LeoIcons
import com.example.leofindit.ui.theme.OnPrimary

@Composable
fun scanButton(
    scanning: Boolean, // Receive scanning state
    onScanToggle: () -> Unit // Receive the toggle function
) {
    Button(
        onClick = { onScanToggle()
                  Log.d("scanButton.onClick", "Button Clicked, onSanToggle should have been called.") }, // Call the passed toggle function
        modifier = Modifier.height(60.dp),
        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
    ) {
        Icon(
            LeoIcons.Bluetooth,
            contentDescription = "Bluetooth Icon",
            tint = OnPrimary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = if (scanning) "Stop" else "Scan", // Use scanning to determine text
            fontSize = 18.sp,
            color = OnPrimary
        )
    }
}
