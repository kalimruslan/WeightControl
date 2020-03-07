package ru.ruslan.weighttracker.dagger.new_modules

import dagger.Binds
import dagger.Module
import ru.ruslan.weighttracker.dagger.scope.CameraScope
import ru.ruslan.weighttracker.domain.contract.CameraContract
import ru.ruslan.weighttracker.ui.camera.CameraPresenter

@Module
abstract class CameraPresentersModule {
    @CameraScope
    @Binds
    abstract fun provideCameraPresenter(presenter: CameraPresenter): CameraContract.Presenter

}
