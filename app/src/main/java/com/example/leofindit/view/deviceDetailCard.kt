import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.leofindit.model.BtleDevice
import com.example.leofindit.ui.theme.LeoIcons
import com.example.leofindit.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceDetailCard(
    device: BtleDevice,
    onSafeClick: (BtleDevice) -> Unit,
    onSuspiciousClick: (BtleDevice) -> Unit,
    onTargetClick: (BtleDevice) -> Unit,
    onNicknameChange: (String) -> Unit,
    onClose: () -> Unit // Add this callback
) {
    var isEditingNickname by remember { mutableStateOf(false) }
    // Initialize nickname state using getNickName()
    var nickname by remember { mutableStateOf(device.getNickName() ?: "") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Top Row: Nickname and Edit Icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isEditingNickname) {
                    TextField(
                        value = nickname,
                        onValueChange = { nickname = it },
                        modifier = Modifier.weight(1f),
                        textStyle = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            cursorColor = Color.Black,
                            focusedIndicatorColor = Color.Gray,
                            unfocusedIndicatorColor = Color.Gray,
                        )
                    )
                    IconButton(onClick = {
                        onNicknameChange(nickname) // Save the nickname
                        isEditingNickname = false
                    }) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Save Nickname",
                            tint = GoldPrimary
                        )
                    }
                } else {
                    // Display nickname using the state variable
                    Text(
                        text = nickname.ifEmpty { "Device NickName" }, // Use nickname or default
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = GoldPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { isEditingNickname = true }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Nickname",
                            tint = GoldPrimary
                        )
                    }
                }
            }

            // Device Details
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    LeoIcons.Bluetooth,
                    contentDescription = "Bluetooth Icon",
                    tint = GoldPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = "Device Name: ${device.deviceName}", color = OnSurface)
                    Text(text = "Manufacturer: ${device.deviceManufacturer}", color = OnSurface)
                    Text(text = "ProductID: TBD", color = OnSurface) // Replace "TBD" with actual data if available
                    Text(text = "UUID: ${device.deviceUuid}", color = OnSurface) // Replace "TBD" with actual data if available
                    Text(text = "Address: ${device.deviceAddress}", color = OnSurface)
                    Text(text = "Device Type: ${device.deviceType}", color = OnSurface)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Switches
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Safe Device", color = OnSurface)
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = device.getIsSafe(), // Use getter
                    onCheckedChange = { isChecked ->
                        onSafeClick(device)
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Suspicious Device", color = OnSurface)
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = device.getIsSuspicious(), // Use getter
                    onCheckedChange = { isChecked ->
                        onSuspiciousClick(device)
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Target Button
            Button(
                onClick = { onTargetClick(device) },
                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Target", color = OnPrimary)
            }
            // Close Button
            Button(
                onClick = onClose, // Call the onClose callback
                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Close", color = OnPrimary)
            }
        }
    }
}