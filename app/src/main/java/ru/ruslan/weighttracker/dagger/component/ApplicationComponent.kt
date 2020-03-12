package ru.ruslan.weighttracker.dagger.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import ru.ruslan.weighttracker.MainApplication
import ru.ruslan.weighttracker.dagger.module.*
import ru.ruslan.weighttracker.dagger.subcomponents.CameraSubComponent
import ru.ruslan.weighttracker.dagger.subcomponents.HomeSubComponent
import ru.ruslan.weighttracker.dagger.subcomponents.SplashSubComponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ApplicationModule::class,
        DatabaseModule::class,
        ViewModelModule::class,
        NetworkModule::class,
        ProfileModule::class,
        UIModule::class,
        SubComponentsModule::class]
)
interface ApplicationComponent {

    fun getCameraComponent(): CameraSubComponent.Factory
    fun getHomeComponent(): HomeSubComponent.Factory
    fun getSplashComponent(): SplashSubComponent.Factory

    fun inject(mainApplication: MainApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}