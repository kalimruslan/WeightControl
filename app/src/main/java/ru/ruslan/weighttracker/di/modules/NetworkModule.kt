package ru.ruslan.weighttracker.di.modules

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.ruslan.weighttracker.BuildConfig
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.data.datasource.api.VideoListNetworkDataSource
import ru.ruslan.weighttracker.data.datasource.api.exceptions.NoConnectivityException
import ru.ruslan.weighttracker.data.datasource.api.retrofit.YoutubeApi
import ru.ruslan.weighttracker.data.repository.VideoListRepositoryImpl
import ru.ruslan.weighttracker.videos.list.domain.VideoListRepository
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class NetworkModule {

    @Singleton
    @Provides
    fun provideYoutubeService(retrofit: Retrofit): YoutubeApi {
        return retrofit.create(YoutubeApi::class.java)
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(context: Context, isNetworkAvailable: Boolean): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .addInterceptor(object : Interceptor{
                override fun intercept(chain: Interceptor.Chain): Response {
                    if(!isNetworkAvailable){
                        throw NoConnectivityException(
                            context.getString(
                                R.string.text_no_internet_connection
                            )
                        )
                    }

                    val builder = chain.request().newBuilder()
                    return chain.proceed(builder.build())
                }

            })
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val newUrl = chain.request().url
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

            })
            .connectTimeout(50, TimeUnit.SECONDS)
            .writeTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun isNetworkAvailable(context: Context): Boolean{
        var connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if(connectivityManager is ConnectivityManager){
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        }
        else false
    }

    @Singleton
    @Provides
    fun provideVideoListRepository(
        dataSource: VideoListNetworkDataSource): VideoListRepository {
        return VideoListRepositoryImpl(dataSource)
    }

    @Singleton
    @Provides
    fun provideVideoListNetworkDataSource(
        youtubeApiService: YoutubeApi): VideoListNetworkDataSource {
        return VideoListNetworkDataSource(youtubeApiService)
    }

}