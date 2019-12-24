package ru.ruslan.weighttracker.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.ruslan.weighttracker.dagger.util.FragmentScope
import ru.ruslan.weighttracker.ui.home.HomeFragment
import ru.ruslan.weighttracker.ui.videos.list.VideosFragment

@Module
abstract class FragmentBindingModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideVideosFragmnet() : VideosFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideHomeFragment(): HomeFragment
}