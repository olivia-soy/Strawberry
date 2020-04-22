package net.soy.strawberry

import android.app.Application
import net.soy.strawberry.di.myDiModule
import org.koin.android.ext.android.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(applicationContext, myDiModule)
    }
}