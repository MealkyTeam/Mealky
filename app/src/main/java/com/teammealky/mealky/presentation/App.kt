package com.teammealky.mealky.presentation

import android.app.Application
import android.content.Context
import android.os.StrictMode
import com.crashlytics.android.Crashlytics
import com.teammealky.mealky.BuildConfig
import com.teammealky.mealky.presentation.commons.injection.component.ApplicationComponent
import com.teammealky.mealky.presentation.commons.injection.component.DaggerApplicationComponent
import com.teammealky.mealky.presentation.commons.injection.module.ApplicationModule
import io.fabric.sdk.android.Fabric
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Fabric.with(this, Crashlytics())
        if (BuildConfig.DEBUG) {
            try {
                Timber.plant(Timber.DebugTree())
                StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                        .detectDiskReads()
                        .detectDiskWrites()
                        .detectNetwork()
                        .penaltyLog()
                        .build())

                StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                        .detectLeakedSqlLiteObjects()
                        .detectLeakedClosableObjects()
                        .penaltyLog()
                        .build())

            } catch (ignored: Exception) {
            }
        }
    }

    companion object {
        fun get(context: Context): App = context.applicationContext as App
    }

    private var graph: ApplicationComponent? = null

    fun getComponent(): ApplicationComponent {
        if (null == graph)
            graph = DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
        return graph!!
    }

    fun setComponent(graph: ApplicationComponent) {
        this.graph = graph
    }

}