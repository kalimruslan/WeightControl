package ru.ruslan.weighttracker.data.datasource.api.exceptions

import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import java.net.UnknownHostException

fun handleNetworkExceptions(ex: Exception): Exception?{
    return when(ex){
        is IOException -> NetworkConnectionException()
        is UnknownHostException -> NetworkConnectionException()
        is HttpException -> apiErrorFromCodeException(ex.code())
        else -> null
    }
}

fun apiErrorFromCodeException(code: Int): Exception {
    return when(code){
        400 -> BadRequestException()
        else -> BadRequestException()
    }
}
