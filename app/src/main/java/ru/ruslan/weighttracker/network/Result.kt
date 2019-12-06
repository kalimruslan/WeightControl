package ru.ruslan.weighttracker.network

import androidx.lifecycle.LiveData

sealed class Result<out T: Any> {
    class Success<out T: Any>(val data: T): Result<T>()
    class Error(val exception: Throwable): Result<Nothing>()
}