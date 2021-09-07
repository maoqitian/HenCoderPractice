package com.mao.leakcanarytest.application

import android.app.Application
import com.kwai.koom.javaoom.KOOM

/**
 *  author : maoqitian
 *  date : 2021/9/7 22:15
 *  description :
 */
class MApplication :Application() {

    override fun onCreate() {
        super.onCreate()
        KOOM.init(this);
    }
}