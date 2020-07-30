package ru.ruslan.weighttracker.dagger.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import ru.ruslan.weighttracker.MainApplication
import ru.ruslan.weighttracker.dagger.module.*
import ru.ruslan.weighttracker.dagger.new_modules.ProfileModule
import ru.ruslan.weighttracker.dagger.subcomponents.*
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        DatabaseModule::class,
        NetworkModule::class,
        ProfileModule::class,
        SubComponentsModule::class]
)
interface ApplicationComponent {

    fun getCameraComponent(): CameraSubComponent.Factory
    fun getHomeComponent(): HomeSubComponent.Factory
    fun getSplashComponent(): SplashSubComponent.Factory
    fun getProfileComponent(): ProfileSubComponent.Factory
    fun getVideoListComponent(): VideoListSubComponent.Factory

    fun inject(mainApplication: MainApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}