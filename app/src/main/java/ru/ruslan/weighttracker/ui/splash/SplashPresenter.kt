package ru.ruslan.weighttracker.ui.splash

import ru.ruslan.weighttracker.dagger.scope.SplashScope
import ru.ruslan.weighttracker.domain.contract.SplashContract
import ru.ruslan.weighttracker.domain.usecase.GetFromProfileUseCase
import javax.inject.Inject

@SplashScope
class SplashPresenter @Inject constructor(private val getFromProfileUseCase: GetFromProfileUseCase) : SplashContract.Presenter{

    private lateinit var splashView: SplashContract.View

    override fun setView(view: SplashContract.View) {
        splashView = view
        splashView.openNextScreen(getFromProfileUseCase.checkIfUserExist())
    }
}