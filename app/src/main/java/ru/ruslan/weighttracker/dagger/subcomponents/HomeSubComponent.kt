package ru.ruslan.weighttracker.dagger.subcomponents

import dagger.Subcomponent
import ru.ruslan.weighttracker.dagger.new_modules.HomePresenterModule
import ru.ruslan.weighttracker.dagger.scope.HomeScope
import ru.ruslan.weighttracker.ui.home.HomeFragment
import ru.ruslan.weighttracker.ui.home.HomePresenter

@HomeScope
@Subcomponent(modules = [HomePresenterModule::class])
interface HomeSubComponent {
    fun inject(fragment: HomeFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(): HomeSubComponent
    }
}