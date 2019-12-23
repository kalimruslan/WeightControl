package ru.ruslan.weighttracker.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import ru.ruslan.weighttracker.MainApplication
import ru.ruslan.weighttracker.di.modules.*
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityModule::class, DatabaseModule::class, NetworkModule::class, ProfileModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(application: MainApplication)
}