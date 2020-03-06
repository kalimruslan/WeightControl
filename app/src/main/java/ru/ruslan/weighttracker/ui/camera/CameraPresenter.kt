package ru.ruslan.weighttracker.ui.camera

import ru.ruslan.weighttracker.domain.contract.camera.CameraContract
import javax.inject.Inject

class CameraPresenter @Inject constructor() : CameraContract.Presenter {

    private lateinit var cameraView: CameraContract.View

    override fun setView(view: CameraContract.View) {
        cameraView = view
        cameraView.initVars()
        cameraView.openCameraPreview()
    }
}