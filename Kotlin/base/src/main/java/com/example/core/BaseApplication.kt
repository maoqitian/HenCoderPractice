package com.example.core

import android.app.Application
import android.content.Context

class BaseApplication : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        currentApplication = this
    }

    companion object {
        @JvmStatic
        @get:JvmName("currentApplication") //外部调用可以直接获取它
        lateinit var currentApplication: Context
         private set //让外部不能调用set 方法
    }
}