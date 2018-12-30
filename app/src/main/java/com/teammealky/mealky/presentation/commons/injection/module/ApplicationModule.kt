package com.teammealky.mealky.presentation.commons.injection.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.teammealky.mealky.data.service.RestDataService
import com.teammealky.mealky.data.executor.InteractorExecutor
import com.teammealky.mealky.data.executor.JobExecutor
import com.teammealky.mealky.domain.executor.PostExecutionThread
import com.teammealky.mealky.domain.executor.ThreadExecutor
import com.teammealky.mealky.domain.executor.UseCaseExecutor
import com.teammealky.mealky.domain.service.RestService
import com.teammealky.mealky.presentation.commons.UIThread
import com.teammealky.mealky.presentation.commons.injection.ApplicationContext
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Provides fun provideApplication(): Application = application
    @Provides @ApplicationContext fun provideContext(): Context = application

    @Provides @Singleton fun provideRestService(service: RestDataService): RestService = service

    @Provides @Singleton fun provideGson(): Gson = Gson()

    @Provides @Singleton
    fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor = jobExecutor

    @Provides @Singleton
    fun providePostExecutionThread(uiThread: UIThread): PostExecutionThread = uiThread

    @Provides @Singleton
    fun provideUseCaseExecutor(executor: InteractorExecutor): UseCaseExecutor = executor
}
