package ru.ruslan.weighttracker.dagger.provider

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.ruslan.weighttracker.dagger.util.FragmentScope
import ru.ruslan.weighttracker.ui.videos.list.VideosFragment

@Module
abstract class VideosFragmentProvider {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideVideosFragmnet() : VideosFragment
}