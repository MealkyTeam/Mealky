package com.teammealky.mealky.data.service

import android.content.Context
import com.teammealky.mealky.BuildConfig
import com.teammealky.mealky.data.net.RestClient
import com.teammealky.mealky.data.net.service.MealkyService
import com.teammealky.mealky.domain.service.RestService
import com.teammealky.mealky.presentation.commons.injection.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestDataService @Inject constructor(@ApplicationContext private val context: Context) : RestService {
    private var client: MealkyService? = null

    override fun client(): MealkyService {
        if (null == client) {
            client = build()
        }
        return client!!
    }

    override fun clientShortTimeout(): MealkyService {
        if (null == client) {
            client = build(TIMEOUT_SHORT_SEC)
        }
        return client!!
    }

    private fun build(timeoutSec: Long = 30L): MealkyService {
        return RestClient.createService(
                BuildConfig.CENTRAL_URL,
                MealkyService::class.java,
                context.cacheDir.absoluteFile,
                timeoutSec)
    }

    companion object {
        private const val TIMEOUT_SHORT_SEC = 5L
    }
}