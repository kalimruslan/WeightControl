package ru.ruslan.weighttracker.data.datasource.api.retrofit

import okhttp3.Interceptor
import okhttp3.Response
import ru.ruslan.weighttracker.BuildConfig

class QueriesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter("part", "snippet, id, contentDetails")
            .addQueryParameter("key", BuildConfig.API_KEY)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}