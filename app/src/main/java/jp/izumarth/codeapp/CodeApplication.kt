package jp.izumarth.codeapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class CodeApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        configureTimber()
    }

    private fun configureTimber() {
        // TODO: set when DEBUG MODE only
        Timber.plant(Timber.DebugTree())
    }
}