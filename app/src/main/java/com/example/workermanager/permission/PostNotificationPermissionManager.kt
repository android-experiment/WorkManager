package com.example.workermanager.permission

import android.Manifest
import android.app.Activity
import android.os.Build
import com.example.workermanager.permission.core.PermissionManager

class PostNotificationPermissionManager(activity: Activity) : PermissionManager(activity) {

    override val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    } else {
        emptyArray()
    }
}
