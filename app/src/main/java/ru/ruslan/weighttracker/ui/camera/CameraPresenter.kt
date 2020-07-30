package ru.ruslan.weighttracker.ui.camera

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ruslan.weighttracker.dagger.scope.CameraScope
import ru.ruslan.weighttracker.domain.contract.CameraContract
import ru.ruslan.weighttracker.domain.usecase.SaveToProfileUseCase
import ru.ruslan.weighttracker.ui.common.FileUtils
import ru.ruslan.weighttracker.ui.util.toString
import java.io.File
import java.util.*
import javax.inject.Inject

@CameraScope
class CameraPresenter @Inject constructor(private val saveToProfileUseCase: SaveToProfileUseCase) :
    CameraContract.Presenter {

    private lateinit var cameraPreviewView: CameraContract.View
    private val fileUtils: FileUtils by lazy { FileUtils() }
    private var inputWeight: String = "0.0"

    override fun setView(view: CameraContract.View) {
        cameraPreviewView = view
        cameraPreviewView.initVars()
        cameraPreviewView.startCamera()
        cameraPreviewView.setListeners()
        cameraPreviewView.needToInputWeightForPhoto()
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
            saveToProfileUseCase.savePhotoData(dateString, file.path, inputWeight,
                object : SaveToProfileUseCase.Callback.Photo {
                    override fun photoSaveSuccess() {
                        cameraPreviewView.closeThisFragment()
                    }

                    override fun photoSaveError() {
                    }
                })
        }
    }


    override fun positiveButtonForInputWeightClicked(weightStr: String) {
        inputWeight = weightStr
        cameraPreviewView.allowToTakePhoto()
    }

    override fun errorSavedImageToFile() {
        cameraPreviewView.showErrorImageSaveToast()
    }
}