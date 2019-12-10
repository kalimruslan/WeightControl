package ru.ruslan.weighttracker.videos.detail

import ru.ruslan.weighttracker.base.BaseContract
import ru.ruslan.weighttracker.base.BasePresenter

interface VideoDetailContract {
    interface View: BaseContract{
        fun initVars()
        fun runVideo(wasRestored: Boolean)
        fun failureVideoRunning()
        fun setListeners()
        fun initViews()
    }

    interface Presenter: BasePresenter<View>{
        fun init()
        fun playerInitializeSucces(wasRestored: Boolean)
        fun playerInitializeError()
    }
}