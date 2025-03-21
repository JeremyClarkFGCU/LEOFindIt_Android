package com.example.leofindit.model

import android.util.Log

data class BtleDevice(
    val deviceType: String,
    val deviceManufacturer: String,
    val deviceName: String,
    val deviceAddress: String?,
    val signalStrength: Int?,
    private var isSafe: Boolean,
    val isParent: Boolean,
    var isTarget: Boolean,
    private var isSuspicious: Boolean,
    val isTag: Boolean,
    private var nickName: String,
    val timeStamp: Long,
    var deviceUuid: String,

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
        Log.i(lTag, "User renamed $deviceType: $oldNickName to $newNickName.")
    }

    fun getNickName(): String? {
        val s: String = nickName
        return s
    }

    fun markSafe(){
        setIsSafe(true)
        setIsSuspicious(false)
        Log.i(lTag, "${getNickName()} ($deviceType) marked as safe.")
    }

    fun markUnsafe(){
        setIsSafe(false)
        Log.i(lTag, "${getNickName()} ($deviceType) marked as unsafe.")
    }

    fun markSuspicious(){
        setIsSuspicious(true)
        setIsSafe(false)
        Log.i(lTag, "${getNickName()} ($deviceType) marked as suspicous.")
    }
    fun markNotSuspicious(){
        setIsSuspicious(false)
        Log.i(lTag, "${getNickName()} ($deviceType) marked as not suspicious.")
    }

    fun markUnknown(){
        setIsSuspicious(false)
        setIsSafe(false)
        Log.i(lTag, "${getNickName()} ($deviceType) marked as unknown.")
    }

}

