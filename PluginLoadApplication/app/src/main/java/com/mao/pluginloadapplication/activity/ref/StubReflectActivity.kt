package com.mao.pluginloadapplication.activity.ref

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mao.pluginloadapplication.activity.BaseStubActivity

/**
 *  author : maoqitian
 *  date : 2021/10/1 10:27
 *  description : 反射调用分发插件Activity生命周期的占坑Activity(代理 proxyActivity)
 */
class StubReflectActivity :BaseStubActivity(){

    //帮助反射调用插件生命周期方法帮助类
    private var reflectLifecycleActivity: ReflectLifecycleActivity? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reflectLifecycleActivity = ReflectLifecycleActivity(activityName,activityClassLoader)
        reflectLifecycleActivity?.attach(this)
        //（生命周期分发）调用插件生命周期方法 onCreate
        reflectLifecycleActivity?.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        reflectLifecycleActivity?.onStart()

    }

    override fun onRestart() {
        super.onRestart()
        reflectLifecycleActivity?.onRestart()

    }

    override fun onResume() {
        super.onResume()
        reflectLifecycleActivity?.onResume()

    }

    override fun onPause() {
        super.onPause()
        reflectLifecycleActivity?.onPause()

    }

    override fun onStop() {
        super.onStop()
        reflectLifecycleActivity?.onStop()

    }

    override fun onDestroy() {
        super.onDestroy()
        reflectLifecycleActivity?.onDestroy()
    }

    companion object{
        //对外暴露启动反射占坑 Activity方法
        fun startPluginActivity(context: Context, pluginPath: String, activityName: String) {
            val intent = Intent(context, StubReflectActivity::class.java)
            intent.putExtra("pluginPath", pluginPath)
            intent.putExtra("activityName", activityName)
            context.startActivity(intent)
        }
    }

}