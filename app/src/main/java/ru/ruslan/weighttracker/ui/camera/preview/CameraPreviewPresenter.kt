package ru.ruslan.weighttracker.ui.camera.preview

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ruslan.weighttracker.domain.contract.camera.CameraPreviewContract
import ru.ruslan.weighttracker.domain.usecase.SaveToProfileUseCase
import ru.ruslan.weighttracker.ui.util.FileUtils
import ru.ruslan.weighttracker.ui.util.toString
import java.io.File
import java.util.*
import javax.inject.Inject

class CameraPreviewPresenter @Inject constructor (private val saveToProfileUseCase: SaveToProfileUseCase) : CameraPreviewContract.Presenter {

    private lateinit var cameraPreviewView: CameraPreviewContract.View
    private val fileUtils: FileUtils by lazy { FileUtils() }

    override fun setView(view: CameraPreviewContract.View) {
        cameraPreviewView = view
        cameraPreviewView.initVars()
        cameraPreviewView.startCamera()
        cameraPreviewView.setListeners()
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

    override fun imageSavedToFile(file: File) {
        val dateString = Calendar.getInstance().time.toString("dd.MM.yyyy")
        CoroutineScope(Dispatchers.Main).launch {
            saveToProfileUseCase.savePhotoData(dateString, file.path,  object : SaveToProfileUseCase.Callback.Photo{
                override fun photoSaveSuccess() {
                    cameraPreviewView.closeThisFragment()
                }

                override fun photoSaveError() {
                }
            })
        }

    }

    override fun errorSavedImageToFile() {
        cameraPreviewView.showErrorImageSaveToast()
    }
}