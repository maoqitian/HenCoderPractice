package com.mao.dynamicload

import android.app.Activity
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View

/**
 *  author : maoqitian
 *  date : 2021/10/1 12:05
 *  description : 插件 Activity 的 基类
 */
open class BasePluginActivity : Activity(),IPluginActivity{

    //分发生命周期的代理 Activity 实例

    var proxyActivity:Activity? = null


    override fun attach(proxyActivity: Activity) {
      this.proxyActivity = proxyActivity
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        if (proxyActivity == null) {
            super.onCreate(savedInstanceState)
        }
    }

    override fun setContentView(layoutResID: Int) {
        proxyActivity?.let {
            it.setContentView(layoutResID)
        } ?: run {
            super.setContentView(layoutResID)
        }
    }

    override fun setContentView(view: View?) {
        proxyActivity?.let {
            it.setContentView(view)
        } ?: run {
            super.setContentView(view)
        }
    }

    override fun onStart() {
        if (proxyActivity == null) {
            super.onStart()
        }
    }

    override fun onRestart() {
        if (proxyActivity == null) {
            super.onRestart()
        }
    }

    override fun onResume() {
        if (proxyActivity == null) {
            super.onResume()
        }
    }

    override fun onPause() {
        if (proxyActivity == null) {
            super.onPause()
        }
    }

    override fun onStop() {
        if (proxyActivity == null) {
            super.onStop()
        }
    }

    override fun onDestroy() {
        if (proxyActivity == null) {
            super.onDestroy()
        }
    }

    override fun getResources(): Resources? {
        if (proxyActivity == null) {
            return super.getResources()
        }
        return proxyActivity?.resources
    }

    override fun getTheme(): Resources.Theme? {
        if (proxyActivity == null) {
            return super.getTheme()
        }
        return proxyActivity?.theme
    }

    override fun getLayoutInflater(): LayoutInflater {
        if (proxyActivity == null) {
            return super.getLayoutInflater()
        }
        return proxyActivity?.layoutInflater!!
    }
}