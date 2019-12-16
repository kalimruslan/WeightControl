package ru.ruslan.weighttracker.data.datasource.api.retrofit

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object NetworkUtil {
    fun isOnline(context: Context): Boolean{
        var connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if(connectivityManager is ConnectivityManager){
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        }
        else false
    }
}