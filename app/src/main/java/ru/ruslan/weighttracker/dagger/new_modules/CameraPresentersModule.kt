package ru.ruslan.weighttracker.dagger.new_modules

import dagger.Binds
import dagger.Module
import ru.ruslan.weighttracker.domain.contract.camera.CameraContract
import ru.ruslan.weighttracker.domain.contract.camera.CameraPreviewContract
import ru.ruslan.weighttracker.ui.camera.CameraPresenter
import ru.ruslan.weighttracker.ui.camera.preview.CameraPreviewPresenter

@Module
abstract class CameraPresentersModule {
    @Binds
    abstract fun provideCameraPresenter(presenter: CameraPresenter): CameraContract.Presenter

    @Binds
    abstract fun provideCameraPreviewPresenter(presenter: CameraPreviewPresenter): CameraPreviewContract.Presenter
}
