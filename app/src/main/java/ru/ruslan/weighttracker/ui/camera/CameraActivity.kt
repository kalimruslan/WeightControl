package ru.ruslan.weighttracker.ui.camera

import android.app.Activity
import android.graphics.Matrix
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import kotlinx.android.synthetic.main.activity_camera.*
import kotlinx.android.synthetic.main.dialog_input_weight_for_photo.view.*
import ru.ruslan.weighttracker.MainApplication
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.dagger.scope.CameraScope
import ru.ruslan.weighttracker.domain.contract.CameraContract
import ru.ruslan.weighttracker.ui.BaseActivity
import ru.ruslan.weighttracker.ui.util.Constants
import ru.ruslan.weighttracker.ui.util.Constants.THIS_APP
import ru.ruslan.weighttracker.ui.util.showToast
import java.io.File
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

@CameraScope
class CameraActivity : BaseActivity(R.layout.activity_camera), CameraContract.View {

    private var animationRotate: Animation? = null
    private var imageCapture: ImageCapture? = null
    private var lensFacing = CameraX.LensFacing.BACK
    @Inject
    lateinit var presenter: CameraContract.Presenter
    private var preview: Preview? = null
    private val executor: Executor by lazy { Executors.newSingleThreadExecutor() }

    override fun initDagger() =
        THIS_APP.getAppComponent().getCameraComponent().create().inject(this)

    override fun initMembers() {
        presenter.setView(this)
        setSupportActionBar(camera_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        camera_toolbar.title = getString(R.string.title_camera)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.camera_toolbar_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.action_camera_lens -> presenter.actionCameraLensViewClicked()
        }
        return true
    }

    override fun initVars() {
        animationRotate = AnimationUtils.loadAnimation(this, R.anim.rotate_center)
    }

    override fun setListeners() {
        iv_take_photo.setOnClickListener {
            it.startAnimation(animationRotate)
            presenter.takePhotoViewClicked()
        }
    }

    override fun startCamera() {
        camera_preview.post {
            CameraX.unbindAll()

            preview = Preview(PreviewConfig.Builder().apply {
                setLensFacing(lensFacing)
                setTargetRotation(camera_preview.display.rotation)
            }.build())

            imageCapture = ImageCapture(
                ImageCaptureConfig.Builder()
                    .apply {
                        setLensFacing(lensFacing)
                        setTargetRotation(camera_preview.display.rotation)
                        setCaptureMode(ImageCapture.CaptureMode.MAX_QUALITY)
                    }.build()
            )

            preview?.setOnPreviewOutputUpdateListener {
                val parent = camera_preview.parent as ViewGroup
                parent.removeView(camera_preview)
                parent.addView(camera_preview, 0)

                camera_preview.surfaceTexture = it.surfaceTexture

                updateTransform()
            }

            CameraX.bindToLifecycle(this, preview, imageCapture)
        }
    }

    private fun updateTransform() {
        val matrix = Matrix()
        val centerX = camera_preview.width / 2f
        val centerY = camera_preview.height / 2f

        val rotationDegrees = when (camera_preview.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)
        camera_preview.setTransform(matrix)
    }

    override fun toggleFrontBackCamera() {
        lensFacing = if (lensFacing == CameraX.LensFacing.BACK) {
            CameraX.LensFacing.FRONT
        } else {
            CameraX.LensFacing.BACK
        }
        camera_preview.post { startCamera() }
    }

    override fun disableAllActions() {
        camera_preview.isClickable = false
        iv_take_photo.isClickable = false
    }

    override fun enableActions() {
        camera_preview.isClickable = true
        iv_take_photo.isClickable = true
    }

    override fun takeAPicture(file: File) {
        imageCapture?.takePicture(file, getMetadata(), executor,
            object : ImageCapture.OnImageSavedListener {
                override fun onImageSaved(file: File) {
                    presenter.imageSavedToFile(file)
                }

                override fun onError(imageCaptureError: ImageCapture.ImageCaptureError,
                                     message: String,
                                     cause: Throwable?) {
                    presenter.errorSavedImageToFile()
                }
            })
    }

    override fun needToInputWeightForPhoto() {
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_input_weight_for_photo, null)
        val alertDialog = AlertDialog.Builder(this).setView(dialogLayout).show()

        dialogLayout.buttonSubmit.setOnClickListener {
            if (dialogLayout.edt_comment.text.isNotEmpty()) {
                presenter.positiveButtonForInputWeightClicked(dialogLayout.edt_comment.text.toString())
                alertDialog.dismiss()
            } else {
                getString(R.string.text_input_weight).showToast(this)
            }
        }
        dialogLayout.buttonCancel.setOnClickListener {
            finish()
            alertDialog.dismiss()
        }
    }

    override fun allowToTakePhoto() {
        iv_take_photo.visibility = View.VISIBLE
    }

    private fun getMetadata() = ImageCapture.Metadata().apply {
        isReversedHorizontal = lensFacing == CameraX.LensFacing.FRONT
    }


    override fun showErrorImageSaveToast() {
        getString(R.string.image_capture_failed).showToast(this)
    }

    override fun closeThisFragment() {
        setResult(Activity.RESULT_OK)
        finish()
    }

}
