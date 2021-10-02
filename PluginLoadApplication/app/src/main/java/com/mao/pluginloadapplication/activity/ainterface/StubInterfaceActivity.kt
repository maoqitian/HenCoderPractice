package com.mao.pluginloadapplication.activity.ainterface

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mao.dynamicload.IPluginActivity
import com.mao.pluginloadapplication.activity.BaseStubActivity

/**
 *  author : maoqitian
 *  date : 2021/10/1 15:38
 *  description : 占桩 通过接口方式分发生命周期 Activity
 */
class StubInterfaceActivity  : BaseStubActivity(){

    private var activity: IPluginActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //获取对应的 插件Activity 实现了对应的  IPluginActivity 接口
        activity = activityClassLoader?.loadClass(activityName)?.newInstance() as IPluginActivity?
        activity?.attach(this)
        activity?.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        activity?.onStart()
    }

    override fun onResume() {
        super.onResume()
        activity?.onResume()
    }

    override fun onPause() {
        super.onPause()
        activity?.onPause()
    }

    override fun onStop() {
        super.onStop()
        activity?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.onDestroy()
    }

    companion object {
        fun startPluginActivity(context: Context, pluginPath: String, activityName: String) {
            val intent = Intent(context, StubInterfaceActivity::class.java)
            intent.putExtra("pluginPath", pluginPath)
            intent.putExtra("activityName", activityName)
            context.startActivity(intent)
        }
    }

}