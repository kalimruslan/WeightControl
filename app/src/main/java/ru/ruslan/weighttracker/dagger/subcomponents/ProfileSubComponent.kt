package ru.ruslan.weighttracker.dagger.subcomponents

import dagger.Subcomponent
import ru.ruslan.weighttracker.dagger.new_modules.ProfilePresentersModule
import ru.ruslan.weighttracker.dagger.scope.ProfileScope
import ru.ruslan.weighttracker.ui.profile.ProfileActivity

@ProfileScope
@Subcomponent(modules = [ProfilePresentersModule::class])
interface ProfileSubComponent {
    fun inject(activity: ProfileActivity)

    @Subcomponent.Factory
    interface Factory{
        fun create(): ProfileSubComponent
    }
}
