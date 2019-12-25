package ru.ruslan.weighttracker.dagger.provider

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.ruslan.weighttracker.dagger.util.FragmentScope
import ru.ruslan.weighttracker.ui.home.HomeFragment

@Module
abstract class HomeFragmentProvider {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideHomeFragment(): HomeFragment
}