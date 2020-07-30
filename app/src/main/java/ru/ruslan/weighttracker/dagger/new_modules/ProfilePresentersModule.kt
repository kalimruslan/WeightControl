package ru.ruslan.weighttracker.dagger.new_modules

import dagger.Binds
import dagger.Module
import ru.ruslan.weighttracker.dagger.scope.ProfileScope
import ru.ruslan.weighttracker.domain.contract.ProfileContract
import ru.ruslan.weighttracker.ui.profile.ProfilePresenter

@Module(includes = [ProfileModule::class])
abstract class ProfilePresentersModule {
    @ProfileScope
    @Binds
    abstract fun provideProfilePresenter(presenter: ProfilePresenter): ProfileContract.Presenter
}
