package ru.ruslan.weighttracker.di.provider

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ru.ruslan.weighttracker.ui.videos.list.VideosFragment

@Module
abstract class VideosFragmentProvider {
    @ContributesAndroidInjector
    abstract fun provideVideosFragment(): VideosFragment
}