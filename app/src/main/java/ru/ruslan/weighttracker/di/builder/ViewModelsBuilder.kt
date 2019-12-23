package ru.ruslan.weighttracker.di.builder

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.ruslan.weighttracker.di.ViewModelKey
import ru.ruslan.weighttracker.ui.profile.vm.ProfileViewModel
import ru.ruslan.weighttracker.ui.videos.list.vm.VideoListViewModel

@Module
abstract class ViewModelsBuilder{

    @Binds
    @IntoMap
    @ViewModelKey(VideoListViewModel::class)
    abstract fun bindVideoListViewModel(videoListViewModel: VideoListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(profileViewModel: ProfileViewModel): ViewModel
}