package ru.ruslan.weighttracker.dagger.subcomponents

import dagger.Subcomponent
import ru.ruslan.weighttracker.dagger.new_modules.CameraPresentersModule
import ru.ruslan.weighttracker.dagger.scope.CameraScope
import ru.ruslan.weighttracker.ui.camera.CameraActivity

@CameraScope
@Subcomponent(modules = [CameraPresentersModule::class])
interface CameraSubComponent {

    fun inject(activity: CameraActivity)

    @Subcomponent.Factory
    interface Factory{
        fun create(): CameraSubComponent
    }
}