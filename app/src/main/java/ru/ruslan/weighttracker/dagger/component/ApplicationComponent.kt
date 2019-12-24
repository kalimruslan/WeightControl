package ru.ruslan.weighttracker.dagger.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import ru.ruslan.weighttracker.MainApplication
import ru.ruslan.weighttracker.dagger.module.*
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, AndroidSupportInjectionModule::class, ProfileModule::class,
    ActivityBindingModule::class, FragmentBindingModule::class, DatabaseModule::class, NetworkModule::class])
interface ApplicationComponent : AndroidInjector<DaggerApplication> {

    fun inject(mainApplication: MainApplication)

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}