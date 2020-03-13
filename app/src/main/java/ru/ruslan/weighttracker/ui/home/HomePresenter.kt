package ru.ruslan.weighttracker.ui.home

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import ru.ruslan.weighttracker.dagger.scope.HomeScope
import ru.ruslan.weighttracker.domain.contract.HomeContract
import ru.ruslan.weighttracker.domain.model.PhotoDataEntity
import ru.ruslan.weighttracker.domain.usecase.GetFromProfileUseCase
import ru.ruslan.weighttracker.ui.PhotoDataEntityToHomeUIMapper
import ru.ruslan.weighttracker.ui.util.Constants
import java.io.File
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

    override fun getDataForPicture(requestCode: Int, filesDir: File) {
        parentJob = coroutineScope.launch {
            getFromProfileUseCase.getDataForPhoto(object :
                GetFromProfileUseCase.Callback.GetDataForPhoto {
                override fun success(photoDataEntity: PhotoDataEntity) {
                    val homeUI = PhotoDataEntityToHomeUIMapper.map(photoDataEntity)
                    saveToJsonFile(homeUI, requestCode, filesDir)
                    homeView.updatePictureViews(homeUI, requestCode)
                }
            })
        }
    }

    private fun saveToJsonFile(homeUI: HomeUI?, requestCode: Int, filesDir: File) {
        val fileName = if(requestCode == Constants.BEFORE_PHOTO_RESULT) "before.json"
        else "after.json"
        val file = File("${filesDir.path}/$fileName" )
        val jsonPretty: String = GsonBuilder().setPrettyPrinting().create().toJson(homeUI)
        file.writeText(jsonPretty)
    }

    override fun getSavedObjects(cacheDir: File) {
        var file = File("${cacheDir.path}/before.json" )
        if(file.exists())
            homeView.updatePictureViews(parseJsonFile(file), Constants.BEFORE_PHOTO_RESULT)

        file = File("${cacheDir.path}/after.json" )
        if(file.exists())
            homeView.updatePictureViews(parseJsonFile(file), Constants.AFTER_PHOTO_RESULT)

    }

    private fun parseJsonFile(file: File) : HomeUI {
        val bufferedReader = file.bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        return Gson().fromJson(inputString, HomeUI::class.java)
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