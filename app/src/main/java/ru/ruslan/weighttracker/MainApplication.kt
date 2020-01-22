package ru.ruslan.weighttracker

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import ru.ruslan.weighttracker.dagger.component.ApplicationComponent
import ru.ruslan.weighttracker.dagger.component.DaggerApplicationComponent
import javax.inject.Inject

class MainApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }


    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}