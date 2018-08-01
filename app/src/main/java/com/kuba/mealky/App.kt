package com.kuba.mealky

import android.app.Application
import timber.log.Timber

class App : Application() {
    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        // Required initialization logic here!
    }


}