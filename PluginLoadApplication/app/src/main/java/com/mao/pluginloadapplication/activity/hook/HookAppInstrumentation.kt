package com.mao.pluginloadapplication.activity.hook

import android.app.Activity
import android.app.Instrumentation
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import com.mao.pluginloadapplication.Logger

/**
 *  author : maoqitian
 *  date : 2021/10/1 15:58
 *  description : Hook 系统 Instrumentation 的 execStartActivity 方法，
 *  同时实现新创建插件 Activity 的方法
 */
class HookAppInstrumentation(var realContext: Context, var base: Instrumentation, var pluginContext: PluginContext):Instrumentation() {

    private val KEY_COMPONENT = "commontec_component"


    companion object{

        fun inject(activity: Activity, pluginContext: PluginContext){
            // hook 系统，替换 Instrumentation 为我们自己的 HookAppInstrumentation，Reflect 是从 VirtualApp 里拷贝的反射工具类
            var reflect = Reflect.on(activity)
            //获取 mMainThread
            var activityThread = reflect.get<Any>("mMainThread")
            //获取 mInstrumentation
            var base = Reflect.on(activityThread).get<Instrumentation>("mInstrumentation")
            //构造我们自己的 HookAppInstrumentation 对象
            var appInstrumentation = HookAppInstrumentation(activity, base, pluginContext)
            //替换
            Reflect.on(activityThread).set("mInstrumentation", appInstrumentation)
            Reflect.on(activity).set("mInstrumentation", appInstrumentation)
        }

    }


    override fun newActivity(cl: ClassLoader, className: String, intent: Intent): Activity? {
        // 创建 Activity 的时候会调用这个方法，在这里需要返回插件 Activity 的实例
        val componentName = intent.getParcelableExtra<ComponentName>(KEY_COMPONENT)
        var clazz = pluginContext.classLoader.loadClass(componentName?.className)
        intent.component = componentName
        return clazz.newInstance() as Activity?
    }


    private fun injectActivity(activity: Activity?) {
        val intent = activity?.intent
        val base = activity?.baseContext
        try {
            Reflect.on(base).set("mResources", pluginContext.resources)
            Reflect.on(activity).set("mResources", pluginContext.resources)
            Reflect.on(activity).set("mBase", pluginContext)
            Reflect.on(activity).set("mApplication", pluginContext.applicationContext)
            // for native activity 根据标识
            val componentName = intent!!.getParcelableExtra<ComponentName>(KEY_COMPONENT)!!
            val wrapperIntent = Intent(intent)
            wrapperIntent.setClassName(componentName.packageName, componentName.className)
            activity.intent = wrapperIntent

        } catch (e: Exception) {
        }
    }

    /**
     * 回调 Activity 的 OnCreate 方法
     */
    override fun callActivityOnCreate(activity: Activity?, icicle: Bundle?) {
        //替换为插件 Activity 的资源
        injectActivity(activity)
        super.callActivityOnCreate(activity, icicle)
    }

    override fun callActivityOnCreate(activity: Activity?, icicle: Bundle?, persistentState: PersistableBundle?) {
        injectActivity(activity)
        super.callActivityOnCreate(activity, icicle, persistentState)
    }


    private fun injectIntent(intent: Intent?) {
        var component: ComponentName? = null
        var oldComponent = intent?.component
        if (component == null || component.packageName == realContext.packageName) {
            // 替换 intent 中的类名为占位 Activity 的类名，这样系统在 Manifest 中查找的时候就可以找到 Activity
            component = ComponentName("com.mao.pluginloadapplication", "com.mao.pluginloadapplication.activity.hook.HookStubActivity")
            intent?.component = component
            intent?.putExtra(KEY_COMPONENT, oldComponent)
        }
    }

    /**
     * 实现启动 Activity 的方法
     */

    fun execStartActivity(
        who: Context,
        contextThread: IBinder,
        token: IBinder,
        target: Activity,
        intent: Intent,
        requestCode: Int
    ): Instrumentation.ActivityResult? {
        Logger.d("exec...")
        injectIntent(intent)
        return Reflect.on(base)
            .call("execStartActivity", who, contextThread, token, target, intent, requestCode).get()
    }

    fun execStartActivity(
        who: Context?,
        contextThread: IBinder?,
        token: IBinder?,
        target: Activity?,
        intent: Intent,
        requestCode: Int,
        options: Bundle?
    ): Instrumentation.ActivityResult? {
        Logger.d("exec...")
        injectIntent(intent)
        return Reflect.on(base)
            .call("execStartActivity", who, contextThread, token, target, intent, requestCode, options ?: Bundle())
            .get()
    }

    fun execStartActivity(
        who: Context,
        contextThread: IBinder,
        token: IBinder,
        target: Fragment,
        intent: Intent,
        requestCode: Int,
        options: Bundle?
    ): Instrumentation.ActivityResult? {
        Logger.d("exec...")
        injectIntent(intent)
        return Reflect.on(base)
            .call("execStartActivity", who, contextThread, token, target, intent, requestCode, options ?: Bundle())
            .get()
    }

    fun execStartActivity(
        who: Context,
        contextThread: IBinder,
        token: IBinder,
        target: String,
        intent: Intent,
        requestCode: Int,
        options: Bundle?
    ): Instrumentation.ActivityResult? {
        Logger.d("exec...")
        injectIntent(intent)
        return Reflect.on(base)
            .call("execStartActivity", who, contextThread, token, target, intent, requestCode, options ?: Bundle())
            .get()
    }

}