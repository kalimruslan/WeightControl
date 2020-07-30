package ru.ruslan.weighttracker.dagger.subcomponents

import dagger.Subcomponent
import ru.ruslan.weighttracker.dagger.new_modules.SplashPresenterModule
import ru.ruslan.weighttracker.dagger.scope.SplashScope
import ru.ruslan.weighttracker.ui.splash.SplashActivity

@SplashScope
@Subcomponent(modules = [SplashPresenterModule::class])
interface SplashSubComponent {
    fun inject(activity: SplashActivity)

    @Subcomponent.Factory
    interface Factory{
        fun create(): SplashSubComponent
    }
}