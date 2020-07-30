package ru.ruslan.weighttracker.dagger.new_modules

import dagger.Binds
import dagger.Module
import ru.ruslan.weighttracker.dagger.scope.VideoListScope
import ru.ruslan.weighttracker.domain.contract.VideoListContract
import ru.ruslan.weighttracker.ui.videos.list.VideoListPresenter

@Module
abstract class VideoListPresenterModule {
    @VideoListScope
    @Binds
    abstract fun provideVideoListPresenter(presenter: VideoListPresenter): VideoListContract.Presenter
}
