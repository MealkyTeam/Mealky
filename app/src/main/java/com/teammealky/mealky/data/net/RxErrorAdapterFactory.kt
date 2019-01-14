package com.teammealky.mealky.data.net

import com.teammealky.mealky.domain.model.APIError
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import timber.log.Timber
import java.lang.reflect.Type

class RxErrorAdapterFactory : CallAdapter.Factory() {

    private val original: RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    override fun get(returnType: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): CallAdapter<*, *>? {
        if (null == returnType || null == retrofit || null == annotations) return null
        if (getRawType(returnType) == retrofit2.Call::class.java) return null
        return RxErrorWrapper(retrofit, original.get(returnType, annotations, retrofit)!!)
    }

    private class RxErrorWrapper<R>(
            val retrofit: Retrofit,
            val wrapped: CallAdapter<R, *>
    ) : CallAdapter<R, Any> {

        override fun adapt(call: Call<R>): Any {
            val adapt = wrapped.adapt(call)
            return when (adapt) {
                is Single<*> -> {
                    adapt.onErrorResumeNext { Single.error(asException(it, buildError(call))) }
                }
                is Completable ->{
                    adapt.onErrorResumeNext { Completable.error(asException(it, buildError(call))) }
                }
                else -> {
                    adapt
                }
            }
        }

        private fun buildError(call: Call<R>): String {
            var url = ""
            try {
                url = call.request().url().toString()
            } catch (ignored: Exception) {
            }
            return url
        }

        override fun responseType(): Type = wrapped.responseType()

        private fun asException(throwable: Throwable, url: String? = null): Exception = when (throwable) {
            is retrofit2.HttpException -> buildError(throwable)
            else -> Exception((if (url?.isNotEmpty() == true) "$url " else "") + throwable.message, throwable)
        }

        private fun buildError(httpException: HttpException): Exception {
            val response: Response<*>?
            val errorBody: ResponseBody
            try {
                response = httpException.response()
                errorBody = (response?.errorBody()
                        ?: (return Exception("Error occurred during parsing API error response")))
            } catch (e: Exception) {
                return Exception("Error occurred during parsing API error response")
            }
            return try {
                val converter = retrofit.responseBodyConverter<APIError>(APIError::class.java, arrayOfNulls(0))
                converter.convert(errorBody) ?: APIError("Something went wrong.")
            } catch (e: Exception) {
                APIError("Something went wrong.")
            }
        }
    }
}