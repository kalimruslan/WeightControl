package ru.ruslan.weighttracker.base

interface BaseContract {
    fun showErrorToast(message: String?)
    fun showLoadingView()
    fun hideLoadingView()
}