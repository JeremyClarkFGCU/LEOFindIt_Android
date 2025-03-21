package com.example.leofindit.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "btle_devices")
data class BTLEDeviceEntity(
    @PrimaryKey val deviceAddress: String,
    val deviceName: String,
    val deviceUUID: String,
    val deviceNickname: String,
    val deviceManufacturer: String,
    val deviceType: String,
    val signalStrength: Int,
    val isSafe: Boolean,
    val isSuspicious: Boolean,
    val isTarget: Boolean
)