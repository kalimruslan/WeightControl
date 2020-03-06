package ru.ruslan.weighttracker.ui.util

import android.content.Intent
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.ui.camera.CameraActivity

object RouterUtil {
    fun openCamera(fragment: Fragment) {
        val cameraIntent = Intent(fragment.context, CameraActivity::class.java)
        fragment.startActivityForResult(cameraIntent, Constants.RESULT_CAMERA)
    }

    fun openGallery(fragment: Fragment) {
        val intent = Intent()
        intent.type = Constants.INTENT_TYPE_IMAGE
        intent.action = Intent.ACTION_GET_CONTENT
        fragment.startActivityForResult(
            Intent.createChooser(
                intent,
                fragment.getString(R.string.text_chooser_image)
            ), Constants.RESULT_GALLERY
        )
    }
}