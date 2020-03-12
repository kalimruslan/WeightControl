package ru.ruslan.weighttracker.dagger.new_modules

import dagger.Binds
import dagger.Module
import ru.ruslan.weighttracker.dagger.scope.SplashScope
import ru.ruslan.weighttracker.domain.contract.SplashContract
import ru.ruslan.weighttracker.ui.splash.SplashPresenter

@Module
abstract class SplashPresenterModule {
    @SplashScope
    @Binds
    abstract fun provideSplashPresenter(presenter: SplashPresenter): SplashContract.Presenter
}
