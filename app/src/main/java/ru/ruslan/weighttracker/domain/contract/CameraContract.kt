package ru.ruslan.weighttracker.domain.contract

import java.io.File

interface CameraContract {
    interface View : BaseView{
        fun startCamera()
        fun setListeners()
        fun toggleFrontBackCamera()
        fun disableAllActions()
        fun takeAPicture(file: File)
        fun showErrorImageSaveToast()
        fun enableActions()
        fun initVars()
        fun closeThisFragment()
        fun needToInputWeightForPhoto(file: File)
    }

    interface Presenter : BasePresenter<View>{
        fun actionCameraLensViewClicked()
        fun takePhotoViewClicked()
        fun imageSavedToFile(file: File)
        fun errorSavedImageToFile()
        fun positiveButtonForInputWeightClicked(file: File, weightStr: String)
        fun negativeButtonForInputWeightClicked(file: File)
        fun onPause()
    }
}