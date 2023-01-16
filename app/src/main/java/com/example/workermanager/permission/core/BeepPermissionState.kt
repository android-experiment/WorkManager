package com.example.workermanager.permission.core

sealed class BeepPermissionState {
    object NotAllowedPermission : BeepPermissionState()
    object AllAllowedPermission : BeepPermissionState()
    object PartiallyAllowedPermission : BeepPermissionState()
}
