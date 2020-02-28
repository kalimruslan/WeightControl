package ru.ruslan.weighttracker.ui.camera.preview

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ruslan.weighttracker.domain.contract.camera.CameraPreviewContract
import ru.ruslan.weighttracker.ui.util.FileUtils
import java.io.File

class CameraPreviewPresenter : CameraPreviewContract.Presenter {

    private lateinit var cameraPreviewView: CameraPreviewContract.View
    private val fileUtils: FileUtils by lazy { FileUtils() }

    override fun setView(view: CameraPreviewContract.View) {
        cameraPreviewView = view
        cameraPreviewView.startCamera()
    }

    override fun actionCameraLensViewClicked() {
        cameraPreviewView.toggleFrontBackCamera()
    }

    override fun takePhotoViewClicked() {
        cameraPreviewView.disableAllActions()

        fileUtils.createDirectoryIfNotExist()
        val file = fileUtils.createFile()

        cameraPreviewView.takeAPicture(file)
    }

    override fun imageSavedToFile(savedFile: File) {
        CoroutineScope(Dispatchers.Main).launch {
            cameraPreviewView.tryToOpenResultFragment(savedFile)
            cameraPreviewView.enableActions()
        }

    }

    override fun errorSavedImageToFile() {
        cameraPreviewView.showErrorImageSaveToast()
    }
}