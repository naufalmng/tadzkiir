package org.destinyardiente.tadzkiir.core

import android.app.Application
import android.content.Context

class MyApplication: Application() {
    companion object {
        var instance: MyApplication? = null
            private set

        val context: Context?
            get() = instance
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
    }


}