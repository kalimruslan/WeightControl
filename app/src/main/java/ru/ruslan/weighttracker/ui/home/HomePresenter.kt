package ru.ruslan.weighttracker.ui.home

import kotlinx.coroutines.*
import ru.ruslan.weighttracker.dagger.scope.HomeScope
import ru.ruslan.weighttracker.domain.contract.HomeContract
import ru.ruslan.weighttracker.domain.model.PhotoDataEntity
import ru.ruslan.weighttracker.domain.usecase.GetFromProfileUseCase
import ru.ruslan.weighttracker.ui.PhotoDataEntityToHomeUIMapper
import ru.ruslan.weighttracker.ui.util.Constants
import javax.inject.Inject

@HomeScope
class HomePresenter @Inject constructor(private val getFromProfileUseCase: GetFromProfileUseCase) :
    HomeContract.Presenter {

    private lateinit var homeView: HomeContract.VIew
    private var isFabOpen: Boolean = false
    private var parentJob: Job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob)

    override fun setView(view: HomeContract.VIew) {
        homeView = view
        homeView.initViews()
        homeView.setListeners()
    }

    override fun onPause() {
        coroutineScope.cancel()
    }

    override fun getDataForPicture(requestCode: Int) {
        parentJob = coroutineScope.launch {
            getFromProfileUseCase.getDataForPhoto(object : GetFromProfileUseCase.Callback.GetDataForPhoto{
                override fun success(photoDataEntity: PhotoDataEntity) {
                    homeView.updatePictureViews(PhotoDataEntityToHomeUIMapper.map(photoDataEntity), requestCode)
                }
            })
        }
    }

    override fun photoBeforeViewClicked() {
        homeView.startCameraScreen(needResult = true, requestCode = Constants.BEFORE_PHOTO_RESULT)
    }

    override fun photoAfterViewClicked() {
        homeView.startCameraScreen(needResult = true, requestCode = Constants.AFTER_PHOTO_RESULT)
    }

    override fun cameraViewClicked() {
        homeView.tryOpenCamera()
    }

    override fun galleryViewClicked() {
        homeView.tryOpenGallery()
    }

    override fun fabMainViewClicked() {
        isFabOpen = if (isFabOpen) {
            homeView.openCloseFabMenu(false)
            false
        } else {
            homeView.openCloseFabMenu(true)
            true
        }
    }

    override fun fabPhotoViewClicked() {
        homeView.showChooseDialog()
    }
}