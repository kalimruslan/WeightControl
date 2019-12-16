package ru.ruslan.weighttracker.base

interface BaseContract {
    fun showErrorToast(message: String?)
    fun showHideLoadingView(isLoading: Boolean)
}