package ru.ruslan.weighttracker.ui.camera

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraX
import androidx.camera.core.ImageCapture
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.custom_view.ImagePopupView

class CameraActivity : AppCompatActivity() {
    private var imageCapture: ImageCapture? = null
    private var imagePopupView: ImagePopupView? = null
    private var lensFacing = CameraX.LensFacing.BACK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
    }
}
