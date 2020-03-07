package ru.ruslan.weighttracker.ui.camera

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ruslan.weighttracker.dagger.scope.CameraScope
import ru.ruslan.weighttracker.domain.contract.CameraContract
import ru.ruslan.weighttracker.domain.usecase.SaveToProfileUseCase
import ru.ruslan.weighttracker.ui.util.FileUtils
import ru.ruslan.weighttracker.ui.util.toString
import java.io.File
import java.util.*
import javax.inject.Inject

@CameraScope
class CameraPresenter @Inject constructor(private val saveToProfileUseCase: SaveToProfileUseCase) :
    CameraContract.Presenter {

    private lateinit var cameraPreviewView: CameraContract.View
    private val fileUtils: FileUtils by lazy { FileUtils() }

    override fun setView(view: CameraContract.View) {
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
            saveToProfileUseCase.savePhotoData(
                dateString,
                file.path,
                "113",
                object : SaveToProfileUseCase.Callback.Photo {
                    override fun photoSaveSuccess() {
                        cameraPreviewView.closeThisFragment()
                    }

                    override fun photoSaveError() {
                    }
                })
        }
    }

    override fun onPause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // TODO нужно удалить сфотканное фото
    override fun negativeButtonForInputWeightClicked(file: File) {

    }

    override fun positiveButtonForInputWeightClicked(file: File, weightStr: String) {

    }

    override fun errorSavedImageToFile() {
        cameraPreviewView.showErrorImageSaveToast()
    }
}