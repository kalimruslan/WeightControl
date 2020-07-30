package ru.ruslan.weighttracker.dagger.subcomponents

import dagger.Subcomponent
import ru.ruslan.weighttracker.dagger.new_modules.VideoListPresenterModule
import ru.ruslan.weighttracker.dagger.scope.VideoListScope
import ru.ruslan.weighttracker.ui.videos.list.VideoListFragment

@VideoListScope
@Subcomponent(modules = [VideoListPresenterModule::class])
interface VideoListSubComponent {
    fun inject(fragment: VideoListFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(): VideoListSubComponent
    }
}
