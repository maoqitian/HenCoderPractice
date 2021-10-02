package com.mao.dynamicload

import android.app.Activity
import android.os.Bundle

/**
 *  author : maoqitian
 *  date : 2021/10/1 12:06
 *  description : 插件Activity 接口，也可通过接口分发的方法启动插件 Activity
 */
interface IPluginActivity {

    fun attach(proxyActivity: Activity)
    fun onCreate(savedInstanceState: Bundle?)
    fun onStart()
    fun onResume()
    fun onRestart()
    fun onPause()
    fun onStop()
    fun onDestroy()
}