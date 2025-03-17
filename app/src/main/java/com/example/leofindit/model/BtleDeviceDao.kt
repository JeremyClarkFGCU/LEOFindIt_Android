package com.example.leofindit.model

import androidx.room.*

@Dao
interface BTLEDeviceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(device: BTLEDeviceEntity)

    @Update
    suspend fun update(device: BTLEDeviceEntity)

    @Delete
    suspend fun delete(device: BTLEDeviceEntity)

    @Query("SELECT * FROM btle_devices")
    suspend fun getAllDevices(): List<BTLEDeviceEntity>

    @Query("SELECT * FROM btle_devices WHERE deviceAddress = :deviceAddress")
    suspend fun getDeviceByAddress(deviceAddress: String): BTLEDeviceEntity?
}