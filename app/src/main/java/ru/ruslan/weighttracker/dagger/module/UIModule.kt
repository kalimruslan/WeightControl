package ru.ruslan.weighttracker.dagger.module

import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector
import ru.ruslan.weighttracker.ui.MainActivity
import ru.ruslan.weighttracker.ui.home.HomeFragment
import ru.ruslan.weighttracker.ui.profile.ProfileActivity
import ru.ruslan.weighttracker.ui.videos.detail.VideoDetailActivity
import ru.ruslan.weighttracker.ui.videos.list.VideosFragment

@Module(includes = [AndroidInjectionModule::class])
abstract class UIModule {

    @ContributesAndroidInjector
    internal abstract fun provideMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun provideVideoDetailActivity(): VideoDetailActivity

    @ContributesAndroidInjector
    internal abstract fun provideProfileActivity(): ProfileActivity

    @ContributesAndroidInjector
    abstract fun provideHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun provideVideosFragment() : VideosFragment
}