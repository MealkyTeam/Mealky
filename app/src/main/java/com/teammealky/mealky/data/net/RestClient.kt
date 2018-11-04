package com.teammealky.mealky.data.net

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object RestClient {
    private const val TIMEOUT_SEC = 30L
    private const val CACHE_SIZE = 2 * 1024 * 1024L


    private fun getHttpClientBuilder(context: Context): OkHttpClient.Builder {

        val okBuilder = OkHttpClient.Builder()

        okBuilder.connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
        okBuilder.readTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
        okBuilder.writeTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)

        okBuilder.addInterceptor { chain ->
            val original = chain.request()
            chain.proceed(original)
        }

        return okBuilder
    }

    fun <S> createService(
            baseURL: String,
            serviceClass: Class<S>,
            cacheDirectory: File? = null,
            context: Context
    ): S {
        val cache = if (null != cacheDirectory) Cache(cacheDirectory, CACHE_SIZE) else null
        val builder = Retrofit.Builder()
                .client(getHttpClientBuilder(context).cache(cache).build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return builder.create(serviceClass)
    }
}