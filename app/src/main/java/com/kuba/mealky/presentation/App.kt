package com.kuba.mealky.presentation

import android.app.Application
import android.content.Context
import com.kuba.mealky.presentation.commons.injection.component.ApplicationComponent
import com.kuba.mealky.presentation.commons.injection.component.DaggerApplicationComponent
import com.kuba.mealky.presentation.commons.injection.module.ApplicationModule
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
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