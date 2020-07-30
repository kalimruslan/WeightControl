package ru.ruslan.weighttracker.ui.common

import android.os.Environment
import java.io.File

class FileUtils {
    companion object {

        private const val IMAGE_PREFIX = "Photo_"
        private const val JPG_SUFFIX = ".jpg"
        private const val FOLDER_NAME = "WeightTrackerPhotos"
    }

    fun createDirectoryIfNotExist() {
        val folder = File(
            Environment.getExternalStorageDirectory().toString() + File.separator + FOLDER_NAME
        )
        if (!folder.exists()) {
            folder.mkdirs()
        }
    }

    fun createFile() = File(
        Environment.getExternalStorageDirectory().toString() + File.separator +
                FOLDER_NAME + File.separator + IMAGE_PREFIX + System.currentTimeMillis() + JPG_SUFFIX
    )
}