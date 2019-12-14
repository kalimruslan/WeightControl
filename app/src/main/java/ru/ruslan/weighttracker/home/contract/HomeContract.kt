package ru.ruslan.weighttracker.home.contract

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
    }

    interface Presenter: BasePresenter<View>{
        fun init()
        fun getPhotoBefore()
        fun getPhotoAfter()
        fun getWeightList()
        fun photoBeforeViewClicked()
        fun photoAfterViewClicked()
        fun mainFabViewClicked()
    }
}