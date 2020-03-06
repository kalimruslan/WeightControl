package ru.ruslan.weighttracker.dagger.subcomponents

import dagger.Subcomponent
import ru.ruslan.weighttracker.dagger.new_modules.CameraPresentersModule
import ru.ruslan.weighttracker.ui.camera.CameraActivity
import ru.ruslan.weighttracker.ui.camera.preview.CameraPreviewFragment

@Subcomponent(modules = [CameraPresentersModule::class])
interface CameraSubComponent {

    fun inject(activity: CameraActivity)
    fun inject(fragment: CameraPreviewFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(): CameraSubComponent
    }
}