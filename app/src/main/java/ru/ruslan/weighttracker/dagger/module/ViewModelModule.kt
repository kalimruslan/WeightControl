package ru.ruslan.weighttracker.dagger.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.ruslan.weighttracker.dagger.util.ViewModelKey
import ru.ruslan.weighttracker.ui.ViewModelFactory
import ru.ruslan.weighttracker.ui.home.vm.HomeViewModel
import ru.ruslan.weighttracker.ui.profile.vm.ProfileViewModel
import ru.ruslan.weighttracker.ui.videos.list.vm.VideoListViewModel

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(VideoListViewModel::class)
    abstract fun provideVideoListViewModel(videoListViewModel: VideoListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun provideHomeViewModel(videoListViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun provideProfileViewModel(videoListViewModel: ProfileViewModel): ViewModel

    @Binds
    abstract fun provideViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}