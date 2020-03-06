package ru.ruslan.weighttracker.domain.contract.camera

import android.net.Uri
import ru.ruslan.weighttracker.domain.contract.BasePresenter
import ru.ruslan.weighttracker.domain.contract.BaseView

interface CameraContract {
    interface View : BaseView{
        fun initVars()
        fun openCameraPreview()
    }

    interface Presenter : BasePresenter<View>{

    }
}