package ru.ruslan.weighttracker.dagger.new_modules

import dagger.Binds
import dagger.Module
import ru.ruslan.weighttracker.dagger.scope.HomeScope
import ru.ruslan.weighttracker.domain.contract.HomeContract
import ru.ruslan.weighttracker.ui.home.HomePresenter

@Module
abstract class HomePresenterModule {
    @HomeScope
    @Binds
    abstract fun provideHomePresenter(presenter: HomePresenter): HomeContract.Presenter
}
