package ru.ruslan.weighttracker.dagger.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context) =
        context.getSharedPreferences("PROFILE_PREFERENCES", Context.MODE_PRIVATE)!!
}