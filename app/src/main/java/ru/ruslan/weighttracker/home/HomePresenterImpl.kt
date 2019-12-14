package ru.ruslan.weighttracker.home

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import ru.ruslan.weighttracker.home.contract.HomeContract
import ru.ruslan.weighttracker.util.ImageUtil

class HomePresenterImpl : HomeContract.Presenter{

    private var homeView: HomeContract.View? = null
    private var isFabOpen = false

    override fun setView(view: HomeContract.View) {
        homeView = view
    }

    override fun init() {
        homeView?.initVars()
        homeView?.initViews()
        homeView?.setListeners()
    }

    override fun getPhotoBefore() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPhotoAfter() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getWeightList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun photoBeforeViewClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun photoAfterViewClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun mainFabViewClicked() {
        isFabOpen = if(isFabOpen){
            homeView?.closeFabMenu()
            false
        } else{
            homeView?.openFabMenu()
            true
        }
    }

    override fun photoFabViewClicked() {
        homeView?.showChooseDialog()
    }

    override fun cameraAppClickedInChooseDialog() {
        homeView?.tryOpenCamera()
    }

    override fun galleryAppClickedInChooseDialog() {
        homeView?.tryOpenGallery()
    }

    override fun resultFromCameraSuccess(homeContext: Context, data: Intent) {
        homeView?.setAfterImageView(ImageUtil.convertUriToBitmap(homeContext, data.data))
    }

    override fun resultFromGallerySuccess(homeContext: Context, data: Intent) {
        homeView?.setAfterImageView(data.extras?.get("data") as Bitmap)
    }

    override fun onStart() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSaveState() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRestoreState() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}