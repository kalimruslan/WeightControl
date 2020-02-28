package ru.ruslan.weighttracker.ui.camera.preview

import android.content.Context
import android.graphics.Matrix
import android.os.Bundle
import android.view.*
import androidx.camera.core.*
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_camera.*
import kotlinx.android.synthetic.main.fragment_camera_preview.*
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.domain.contract.camera.CameraPreviewContract
import ru.ruslan.weighttracker.ui.camera.CameraActivity
import ru.ruslan.weighttracker.ui.util.showToast
import java.io.File
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class CameraPreviewFragment : Fragment(), CameraPreviewContract.View {

    companion object {
        @JvmStatic
        fun newInstance() = CameraPreviewFragment()
    }

    private var imageCapture: ImageCapture? = null
    private var lensFacing = CameraX.LensFacing.BACK
    private lateinit var presenter: CameraPreviewContract.Presenter
    private var preview: Preview? = null
    private lateinit var cameraActivity: CameraActivity
    private val executor: Executor by lazy { Executors.newSingleThreadExecutor() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        cameraActivity = context as CameraActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_camera_preview, container, false)
        cameraActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        cameraActivity.camera_toolbar.title = "Камера"
        presenter = CameraPreviewPresenter()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.setView(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.camera_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> cameraActivity.onBackPressed()
            R.id.action_camera_lens -> presenter.actionCameraLensViewClicked()
        }
        return true
    }


    override fun setListeners() {
        iv_take_photo.setOnClickListener{presenter.takePhotoViewClicked()}
    }

    override fun startCamera() {
        camera_preview.post{
            CameraX.unbindAll()

            preview = Preview(PreviewConfig.Builder().apply {
                setLensFacing(lensFacing)
                setTargetRotation(camera_preview.display.rotation)
            }.build())

            imageCapture = ImageCapture(ImageCaptureConfig.Builder()
                .apply {
                    setLensFacing(lensFacing)
                    setTargetRotation(camera_preview.display.rotation)
                    setCaptureMode(ImageCapture.CaptureMode.MAX_QUALITY)
                }.build())

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

    private fun getMetadata() = ImageCapture.Metadata().apply {
        isReversedHorizontal = lensFacing == CameraX.LensFacing.FRONT
    }

    override fun tryToOpenResultFragment(savedFile: File) {
        val uri = FileProvider.getUriForFile(cameraActivity, cameraActivity.packageName, savedFile)
        cameraActivity.openCameraResult(uri)
    }

    override fun showErrorImageSaveToast() {
        getString(R.string.image_capture_failed).showToast(cameraActivity)
    }
}