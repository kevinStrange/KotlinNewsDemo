package com.example.huanghongfa.kotlinnewsdemo.utils

import android.content.Context
import android.provider.Settings

/**
 * Created by huanghongfa on 2017/6/21.
 * 主要是获取Android手机设备的一些信息，比如deviceId
 */
object DeviceInfo {

    /**
     * 获取安卓设备ID
     */
    fun getDeviceIDByAndroidID(context: Context): String {
        return Settings.System.getString(context.contentResolver, Settings.System.ANDROID_ID)
    }
}