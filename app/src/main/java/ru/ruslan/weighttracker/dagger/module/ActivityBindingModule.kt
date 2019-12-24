package ru.ruslan.weighttracker.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.ruslan.weighttracker.dagger.util.ActivityScope
import ru.ruslan.weighttracker.ui.MainActivity
import ru.ruslan.weighttracker.ui.profile.ProfileActivity
import ru.ruslan.weighttracker.ui.videos.detail.VideoDetailActivity

@Module
abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [FragmentBindingModule::class])
    abstract fun provideMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [FragmentBindingModule::class])
    abstract fun provideVideoDetailActivity(): VideoDetailActivity

    @ActivityScope
    @ContributesAndroidInjector()
    abstract fun provideProfileActivity(): ProfileActivity
}