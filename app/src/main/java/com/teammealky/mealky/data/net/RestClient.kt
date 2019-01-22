package com.teammealky.mealky.data.net

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.teammealky.mealky.BuildConfig
import okhttp3.Cache
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit

object RestClient {
    private const val CACHE_SIZE = 2 * 1024 * 1024L

    private fun getHttpClientBuilder(timeoutSec: Long): OkHttpClient.Builder {

        val okBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor(
                    HttpLoggingInterceptor.Logger { Timber.tag("HTTP").v(it) })
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            okBuilder.addInterceptor(loggingInterceptor)
        }

        okBuilder.connectTimeout(timeoutSec, TimeUnit.SECONDS)
        okBuilder.readTimeout(timeoutSec, TimeUnit.SECONDS)
        okBuilder.writeTimeout(timeoutSec, TimeUnit.SECONDS)

        okBuilder.addInterceptor { chain ->
            val authToken = Credentials.basic(BuildConfig.CENTRAL_LOGIN, BuildConfig.CENTRAL_PASSWORD)

            var request = chain.request()
            val headers = request.headers().newBuilder().add("Authorization", authToken).build()
            request = request.newBuilder().headers(headers).build()
            chain.proceed(request)
        }

        return okBuilder
    }

    private val gson: Gson by lazy {
        GsonBuilder()
                .create()
    }

    fun <S> createService(
            baseURL: String,
            serviceClass: Class<S>,
            cacheDirectory: File? = null,
            timeoutSec: Long
    ): S {
        val cache = if (null != cacheDirectory) Cache(cacheDirectory, CACHE_SIZE) else null
        val builder = Retrofit.Builder()
                .client(getHttpClientBuilder(timeoutSec).cache(cache).build())
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxErrorAdapterFactory())
                .build()

        return builder.create(serviceClass)
    }
}