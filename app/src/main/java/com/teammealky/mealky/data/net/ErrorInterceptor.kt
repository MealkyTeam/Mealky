package com.teammealky.mealky.data.net

import okhttp3.Interceptor
import okhttp3.Response

class ErrorInterceptor: Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        //todo need to be implemented

        return response
    }

}