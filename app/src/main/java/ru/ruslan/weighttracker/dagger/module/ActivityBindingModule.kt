package ru.ruslan.weighttracker.dagger.module

import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ru.ruslan.weighttracker.dagger.provider.HomeFragmentProvider
import ru.ruslan.weighttracker.dagger.provider.VideosFragmentProvider
import ru.ruslan.weighttracker.dagger.util.ActivityScope
import ru.ruslan.weighttracker.ui.MainActivity
import ru.ruslan.weighttracker.ui.profile.ProfileActivity
import ru.ruslan.weighttracker.ui.videos.detail.VideoDetailActivity

@Module(includes = [AndroidInjectionModule::class])
interface ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [HomeFragmentProvider::class, VideosFragmentProvider::class])
    fun provideMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector
    fun provideVideoDetailActivity(): VideoDetailActivity

    @ActivityScope
    @ContributesAndroidInjector
    fun provideProfileActivity(): ProfileActivity
}