package dev.sukhrob.carstore.app

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.google.firebase.FirebaseApp
import com.zeugmasolutions.localehelper.LocaleHelper
import com.zeugmasolutions.localehelper.LocaleHelperApplicationDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    private val localeAppDelegate = LocaleHelperApplicationDelegate()

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(localeAppDelegate.attachBaseContext(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeAppDelegate.onConfigurationChanged(this)
    }

    override fun getApplicationContext(): Context {
        return LocaleHelper.onAttach(super.getApplicationContext())
    }
}