package com.mao.pluginloadapplication

import android.app.Application
import android.content.Context
import me.weishu.reflection.Reflection

/**
 *  author : maoqitian
 *  date : 2021/10/1 16:56
 *  description :
 */
class PluginLoadApplication :Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Reflection.unseal(base)
    }
}