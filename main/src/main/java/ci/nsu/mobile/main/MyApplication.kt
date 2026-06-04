package ci.nsu.mobile.main

import android.app.Application
import ci.nsu.mobile.main.network.TokenManager

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        TokenManager.init(this)
    }
}