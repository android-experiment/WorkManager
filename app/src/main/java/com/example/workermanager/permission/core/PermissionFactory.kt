package com.example.workermanager.permission.core

import android.app.Activity
import com.example.workermanager.permission.LocationPermissionManager
import com.example.workermanager.permission.PostNotificationPermissionManager
import com.example.workermanager.permission.StoragePermissionManager

class PermissionFactory {
    fun <PM : PermissionManager> create(activity: Activity, managerClass: Class<PM>): PM {
        return when (managerClass) {
            StoragePermissionManager::class.java -> StoragePermissionManager(activity)
            LocationPermissionManager::class.java -> LocationPermissionManager(activity)
            PostNotificationPermissionManager::class.java -> PostNotificationPermissionManager(activity)
            else -> throw IllegalArgumentException("존재하지 않는 Permission Manager입니다.")
        } as PM
    }
}
