package ru.ruslan.weighttracker.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import ru.ruslan.weighttracker.MainApplication
import ru.ruslan.weighttracker.di.builder.ViewModelFactoryBuilder
import javax.inject.Singleton

@Module(includes = [ViewModelFactoryBuilder::class])
class AppModule {
    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext
}