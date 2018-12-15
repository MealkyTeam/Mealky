package com.teammealky.mealky.data.net

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.teammealky.mealky.BuildConfig
import okhttp3.Cache
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object RestClient {
    private const val TIMEOUT_SEC = 35L
    private const val CACHE_SIZE = 2 * 1024 * 1024L


    private fun getHttpClientBuilder(context: Context): OkHttpClient.Builder {

        val okBuilder = OkHttpClient.Builder()

        okBuilder.connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
        okBuilder.readTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
        okBuilder.writeTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)

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
            context: Context
    ): S {
        val cache = if (null != cacheDirectory) Cache(cacheDirectory, CACHE_SIZE) else null
        val builder = Retrofit.Builder()
                .client(getHttpClientBuilder(context).cache(cache).build())
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxErrorAdapterFactory())
                .build()

        return builder.create(serviceClass)
    }
}