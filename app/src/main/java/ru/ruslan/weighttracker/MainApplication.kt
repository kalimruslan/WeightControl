package ru.ruslan.weighttracker

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import ru.ruslan.weighttracker.di.component.DaggerAppComponent
import javax.inject.Inject
import kotlin.properties.Delegates

class MainApplication : Application(), HasAndroidInjector{

    @Inject
    lateinit var activityInjector:DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)

        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)

    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {
        var instance: MainApplication by Delegates.notNull()
    }

    override fun androidInjector(): AndroidInjector<Any>? = activityInjector

}