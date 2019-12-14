package ru.ruslan.weighttracker.home.contract

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import ru.ruslan.weighttracker.base.BaseContract
import ru.ruslan.weighttracker.base.BasePresenter

interface HomeContract {
    interface View: BaseContract{
        fun initVars()
        fun initViews()
        fun setListeners()
        fun populateWeightAdapter()
        fun closeFabMenu()
        fun openFabMenu()
        fun showChooseDialog()
        fun tryOpenCamera()
        fun tryOpenGallery()
        fun setAfterImageView(bitmap: Bitmap?)
    }

    interface Presenter: BasePresenter<View>{
        fun init()
        fun getPhotoBefore()
        fun getPhotoAfter()
        fun getWeightList()
        fun photoBeforeViewClicked()
        fun photoAfterViewClicked()
        fun mainFabViewClicked()
        fun photoFabViewClicked()
        fun cameraAppClickedInChooseDialog()
        fun galleryAppClickedInChooseDialog()
        fun resultFromCameraSuccess(homeContext: Context, data: Intent)
        fun resultFromGallerySuccess(homeContext: Context, data: Intent)
    }
}