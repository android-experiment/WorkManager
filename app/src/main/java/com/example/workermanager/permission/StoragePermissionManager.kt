package com.example.workermanager.permission

import android.Manifest
import android.app.Activity
import android.os.Build
import com.example.workermanager.permission.core.PermissionManager

class StoragePermissionManager(activity: Activity) : PermissionManager(activity) {

    override val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
    } else {
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
}
