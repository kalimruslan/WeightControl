package ru.ruslan.weighttracker.ui.util

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ru.ruslan.weighttracker.ui.util.Constants.APP_ACTIVITY

const val CAMERA_PERMISSION = Manifest.permission.CAMERA
const val READ_EXTERNAL_STORAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE
const val WRITE_EXTERNAL_STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE

const val CAMERA_PERMISSION_REQUEST = 401
const val EXTERNAL_PERMISSION_REQUEST = 402

fun checkPermission(permission: String, permRequest: Int): Boolean{
    return if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(APP_ACTIVITY, permission)
        != PackageManager.PERMISSION_GRANTED){
        ActivityCompat.requestPermissions(APP_ACTIVITY, arrayOf(permission), permRequest)
        false
    } else true
}