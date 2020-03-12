package ru.ruslan.weighttracker.domain.contract

import ru.ruslan.weighttracker.ui.home.HomeUI

interface HomeContract {
    interface VIew: BaseView{
        fun initViews()
        fun setListeners()
        fun openCloseFabMenu(isOpen: Boolean)
        fun updatePictureViews(profile: HomeUI?,
                               requestCode: Int)
        fun startCameraScreen(needResult: Boolean, requestCode: Int = 0)
        fun showChooseDialog()
        fun tryOpenCamera()
        fun tryOpenGallery()
        fun showToastForUserNotExist()
        fun startProfileScreen()
    }

    interface Presenter : BasePresenter<VIew>{
        fun getDataForPicture(requestCode: Int)
        fun photoBeforeViewClicked()
        fun photoAfterViewClicked()
        fun fabMainViewClicked()
        fun fabPhotoViewClicked()
        fun cameraViewClicked()
        fun galleryViewClicked()
    }

}