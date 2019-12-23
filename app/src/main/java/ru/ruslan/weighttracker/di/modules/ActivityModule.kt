package ru.ruslan.weighttracker.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ru.ruslan.weighttracker.di.provider.VideosFragmentProvider
import ru.ruslan.weighttracker.ui.MainActivity
import ru.ruslan.weighttracker.ui.profile.ProfileActivity

@Module(includes = [AndroidSupportInjectionModule::class])
interface ActivityModule {
    @ContributesAndroidInjector(
        modules = [
            VideosFragmentProvider::class
        ])
    fun mainActivityInjector(): MainActivity
    fun profileActivityInjector(): ProfileActivity
}