package ru.ruslan.weighttracker.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ru.ruslan.weighttracker.MainActivity
import ru.ruslan.weighttracker.di.provider.VideosFragmentProvider
import ru.ruslan.weighttracker.videos.list.ui.VideosFragment

@Module(includes = [AndroidSupportInjectionModule::class])
interface ActivityModule {
    @ContributesAndroidInjector(
        modules = [
            VideosFragmentProvider::class
        ])
    fun mainActivityInjector(): MainActivity
}