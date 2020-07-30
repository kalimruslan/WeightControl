package ru.ruslan.weighttracker

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import ru.ruslan.weighttracker.dagger.component.ApplicationComponent
import ru.ruslan.weighttracker.dagger.component.DaggerApplicationComponent
import ru.ruslan.weighttracker.ui.util.Constants.THIS_APP

class MainApplication : Application() {

    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        THIS_APP = this
        MultiDex.install(this)
        applicationComponent =  DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
        applicationComponent.inject(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    fun getAppComponent(): ApplicationComponent = applicationComponent
}