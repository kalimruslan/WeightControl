package ru.ruslan.weighttracker.ui.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.io.ByteArrayOutputStream

object ImageUtil {
    fun convertUriToBitmap(context: Context?, uri: Uri?): Bitmap? {
        uri?.let {
            return MediaStore.Images.Media.getBitmap(context?.contentResolver, it)
        } ?: return null
    }

    fun getImageUri(context: Context?, bitmap: Bitmap): Uri{
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(context?.contentResolver, bitmap, "Title", "")
        return Uri.parse(path)
    }
}