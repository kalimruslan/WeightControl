package ru.ruslan.weighttracker.ui.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtils {

    const val CODE_CAMERA_PERMISSION = 401
    const val CODE_EXTERNAL_PERMISSION = 402

    fun checkAndRequestCameraPermissions(context: Context?): Boolean{
        context?.let {
            val permissionCamera = ContextCompat.checkSelfPermission(it, Manifest.permission.CAMERA)

            val permissionReadStorage =
                ContextCompat.checkSelfPermission(it, Manifest.permission.READ_EXTERNAL_STORAGE)
            val permissionWriteStorage =
                ContextCompat.checkSelfPermission(it, Manifest.permission.WRITE_EXTERNAL_STORAGE)

            val listPermissionsNeeded = ArrayList<String>()
            if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CAMERA)
            }
            if (permissionReadStorage != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            if (permissionWriteStorage != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            if (listPermissionsNeeded.isNotEmpty()) {
                ActivityCompat.requestPermissions(it as Activity, listPermissionsNeeded.toTypedArray(), CODE_CAMERA_PERMISSION)
                return false
            }
            return true
        }
        return false
    }

    fun checkAndRequestExternalPermission(context: Context?): Boolean{
        context?.let {
            val permissionReadStorage =
                ContextCompat.checkSelfPermission(it, Manifest.permission.READ_EXTERNAL_STORAGE)
            val permissionWriteStorage =
                ContextCompat.checkSelfPermission(it, Manifest.permission.WRITE_EXTERNAL_STORAGE)

            val listPermissionsNeeded = ArrayList<String>()
            if (permissionReadStorage != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            if (permissionWriteStorage != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            if (listPermissionsNeeded.isNotEmpty()) {
                ActivityCompat.requestPermissions(it as Activity, listPermissionsNeeded.toTypedArray(), CODE_EXTERNAL_PERMISSION)
                return false
            }
            return true
        }
        return false
    }
}