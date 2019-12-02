package ru.ruslan.weighttracker.base

interface BasePresenter<in T: BaseContract> {
    fun setView(view: T)
}