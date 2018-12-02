package com.teammealky.mealky.presentation.commons.injection.module

import android.app.Application
import android.content.Context
import com.teammealky.mealky.data.service.RestDataService
import com.teammealky.mealky.data.executor.InteractorExecutor
import com.teammealky.mealky.data.executor.JobExecutor
import com.teammealky.mealky.data.repository.AuthorizationDataRepository
import com.teammealky.mealky.data.repository.MealsDataRepository
import com.teammealky.mealky.domain.executor.PostExecutionThread
import com.teammealky.mealky.domain.executor.ThreadExecutor
import com.teammealky.mealky.domain.executor.UseCaseExecutor
import com.teammealky.mealky.domain.repository.AuthorizationRepository
import com.teammealky.mealky.domain.repository.MealsRepository
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

    @Provides @Singleton
    fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor = jobExecutor

    @Provides @Singleton
    fun providePostExecutionThread(uiThread: UIThread): PostExecutionThread = uiThread

    @Provides @Singleton
    fun provideUseCaseExecutor(executor: InteractorExecutor): UseCaseExecutor = executor

    @Provides @Singleton fun provideMealsRepo(repo: MealsDataRepository): MealsRepository = repo

    @Provides @Singleton
    fun provideAuthRepo(repo: AuthorizationDataRepository): AuthorizationRepository = repo

    @Provides @Singleton
    fun provideHttpClient(): OkHttpClient {
        val okBuilder = OkHttpClient.Builder()

        okBuilder.connectTimeout(10, TimeUnit.SECONDS)
        okBuilder.readTimeout(10, TimeUnit.SECONDS)
        okBuilder.writeTimeout(10, TimeUnit.SECONDS)

        return okBuilder.build()
    }
}
