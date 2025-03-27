package com.example.leofindit.model

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


data class BtleDevice(
    val deviceType: String,
    val deviceManufacturer: String,
    val deviceName: String,
    val deviceAddress: String,
    val signalStrength: Int,
    private var isSafe: Boolean,
    val isParent: Boolean,
    var isTarget: Boolean,
    private var isSuspicious: Boolean,
    val isTag: Boolean,
    private var nickName: String,
    val timeStamp: Long,
    var deviceUuid: String,

    val btleDeviceDao: BTLEDeviceDao, // Local scope for interacting with db
    val database: AppDatabase,
    val coroutineScope: CoroutineScope
){

    private var lTag: String = "BTLEDevice"

    private fun setIsSuspicious(suspicious: Boolean){
        isSuspicious = suspicious
    }
    fun getIsSuspicious():Boolean{
        return isSuspicious
    }

    private fun setIsSafe(safe: Boolean){
        isSafe = safe
    }
    fun getIsSafe(): Boolean {
        return isSafe
    }
    fun getIsTarget(): Boolean {
        return isTarget
    }

    fun setNickName(newNickName: String){
        var oldNickName = getNickName()
        nickName = newNickName

        CoroutineScope(Dispatchers.IO).launch {
            updateDatabase()
            Log.i(lTag, "User renamed $deviceType: $oldNickName to $newNickName.")
        }
    }

    fun getNickName(): String? {
        val s: String = nickName
        return s
    }

    fun markSafe(){
        setIsSafe(true)
        setIsSuspicious(false)

        CoroutineScope(Dispatchers.IO).launch { // Launch coroutine to safely update DB
            updateDatabase()
            Log.i(lTag, "${getNickName()} ($deviceType) marked as safe.")
            Log.d(lTag, "${btleDeviceDao.getAllDevices()}")
        }
    }

    fun markUnsafe(){
        setIsSafe(false)
        Log.i(lTag, "${getNickName()} ($deviceType) marked as unsafe.")
    }

    fun markSuspicious(){
        setIsSuspicious(true)
        setIsSafe(false)

        CoroutineScope(Dispatchers.IO).launch { // Launch coroutine to safely update DB
            updateDatabase()
            Log.i(lTag, "${getNickName()} ($deviceType) marked as suspicious.")
            Log.d(lTag, "${btleDeviceDao.getAllDevices()}")
        }

    }
    fun markNotSuspicious(){
        setIsSuspicious(false)
        Log.i(lTag, "${getNickName()} ($deviceType) marked as not suspicious.")
    }

    fun markUnknown(){
        setIsSuspicious(false)
        setIsSafe(false)

        CoroutineScope(Dispatchers.IO).launch { // Launch coroutine to safely update DB
            updateDatabase()
            Log.i(lTag, "${getNickName()} ($deviceType) marked as unknown.")
        }
    }

    suspend fun updateDatabase() {
        // Create a BTLEDeviceEntity with params from BtleDevice object to call btleDeviceDao update funct.
        val deviceEntity = BTLEDeviceEntity(
            deviceName = this@BtleDevice.deviceName,
            deviceNickname = this@BtleDevice.nickName,
            deviceUUID = this@BtleDevice.deviceUuid,     // RENAME this param for consistency!!!
            deviceAddress = this@BtleDevice.deviceAddress,
            deviceManufacturer = this@BtleDevice.deviceManufacturer,
            deviceType = this@BtleDevice.deviceType,
            signalStrength = this@BtleDevice.signalStrength,
            isSafe = this@BtleDevice.getIsSafe(),
            isSuspicious = this@BtleDevice.getIsSuspicious(),
            isTarget = this@BtleDevice.getIsTarget()
        )

        btleDeviceDao.update(deviceEntity)
    }

}

