package ru.ruslan.weighttracker.domain.contract

import ru.ruslan.weighttracker.ui.home.HomeUI
import java.io.File

interface HomeContract {
    interface VIew: BaseView{
        fun initViews()
        fun setListeners()
        fun updatePictureViews(profile: HomeUI?, requestCode: Int)
        fun startCameraScreen(needResult: Boolean, requestCode: Int = 0)
        fun errorForLoadingWeightList(errorText: String)
        fun populateWeightAdapter(weights: List<HomeUI>?)
    }

    interface Presenter : BasePresenter<VIew>{
        fun getDataForPicture(requestCode: Int, filesDir: File)
        fun photoBeforeViewClicked()
        fun photoAfterViewClicked()
        fun getSavedObjects(cacheDir: File)
        fun getWeightList()
    }

}