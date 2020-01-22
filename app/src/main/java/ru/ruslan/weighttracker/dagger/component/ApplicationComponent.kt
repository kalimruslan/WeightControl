package ru.ruslan.weighttracker.dagger.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import ru.ruslan.weighttracker.MainApplication
import ru.ruslan.weighttracker.dagger.module.*
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
        UIModule::class]
)
interface ApplicationComponent {

    fun inject(mainApplication: MainApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}