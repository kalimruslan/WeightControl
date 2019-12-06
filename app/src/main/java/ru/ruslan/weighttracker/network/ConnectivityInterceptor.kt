package ru.ruslan.weighttracker.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import ru.ruslan.weighttracker.R

class ConnectivityInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!NetworkUtil.isOnline(context = context)){
            throw NoConnectivityException(context.getString(R.string.text_no_internet_connection))
        }

        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

}
