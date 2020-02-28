package ru.ruslan.weighttracker.domain.contract

interface BasePresenter<in T : BaseView> {
    fun setView(view: T)
}
