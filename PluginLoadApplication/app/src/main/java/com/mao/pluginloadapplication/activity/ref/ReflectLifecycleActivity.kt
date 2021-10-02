package com.mao.pluginloadapplication.activity.ref

import android.app.Activity
import android.os.Bundle
import java.lang.reflect.Method

/**
 *  author : maoqitian
 *  date : 2021/10/1 10:11
 *  description : 反射调用 Activity 生命周期方法的封装
 */
class ReflectLifecycleActivity(activity: String,activityClassLoader:ClassLoader?) {

    // Activity class 对象
    private var clazz: Class<Activity> = activityClassLoader?.loadClass(activity) as Class<Activity>

    //需要反射分发生命周期的Activity实例
    private var activity: Activity = clazz.newInstance()


    //调用插件 activity 的 attach 方法
    fun attach(proxyActivity: Activity?){
        getMethod("attach",Activity::class.java).invoke(activity,proxyActivity)
    }


    fun onCreate(savedInstanceState: Bundle?) {
        getMethod("onCreate", Bundle::class.java).invoke(activity, savedInstanceState)
    }

    fun onStart() {
        getMethod("onStart").invoke(activity)
    }

    fun onRestart() {
        getMethod("onRestart").invoke(activity)
    }

    fun onResume() {
        getMethod("onResume").invoke(activity)
    }

    fun onPause() {
        getMethod("onPause").invoke(activity)
    }

    fun onStop() {
        getMethod("onStop").invoke(activity)
    }

    fun onDestroy() {
        getMethod("onDestroy").invoke(activity)
    }


    //反射获取对应的方法
    fun getMethod(methodName: String, vararg params: Class<*>):Method{
        return clazz.getMethod(methodName,*params)

    }

}