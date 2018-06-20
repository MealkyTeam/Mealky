package com.kuba.mealky.Database

import android.os.Handler
import android.os.HandlerThread

class DBWorkerThread(threadName: String) : HandlerThread(threadName) {

    private lateinit var mWorkerHandler: Handler

    override fun start() {
        super.start()
        mWorkerHandler = Handler(looper)

    }

    fun postTask(task: Runnable) {
        mWorkerHandler.post(task)
    }

}