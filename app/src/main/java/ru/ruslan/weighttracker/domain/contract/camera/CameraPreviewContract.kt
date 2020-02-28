package ru.ruslan.weighttracker.domain.contract.camera

import ru.ruslan.weighttracker.domain.contract.BasePresenter
import ru.ruslan.weighttracker.domain.contract.BaseView
import java.io.File

interface CameraPreviewContract {
    interface View : BaseView {
        fun startCamera()
        fun setListeners()
        fun toggleFrontBackCamera()
        fun disableAllActions()
        fun takeAPicture(file: File)
        fun showErrorImageSaveToast()
        fun tryToOpenResultFragment(savedFile: File)
        fun enableActions()
    }

    interface Presenter :
        BasePresenter<View> {
        fun actionCameraLensViewClicked()
        fun takePhotoViewClicked()
        fun imageSavedToFile(file: File)
        fun errorSavedImageToFile()

    }
}