package ru.ruslan.weighttracker.domain.contract

interface SplashContract {
    interface View : BaseView{
        fun openNextScreen(userExist: Boolean)
    }

    interface Presenter : BasePresenter<View>
}