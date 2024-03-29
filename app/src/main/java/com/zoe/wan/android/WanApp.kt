package com.zoe.wan.android

import android.app.Application
import com.zoe.wan.http.RetrofitClient

class WanApp:Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitClient.getInstance().setContext(applicationContext)
    }
}
