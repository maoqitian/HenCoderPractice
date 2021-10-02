package com.mao.pluginloadapplication.activity

import android.app.Activity
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.util.Log
import dalvik.system.DexClassLoader
import java.io.File
import java.lang.reflect.Method

/**
 *  author : maoqitian
 *  date : 2021/10/1 10:28
 *  description : 插件化 占坑 Activity 基类 ，提供一些 占坑Activity 基础实现封装
 */
open class BaseStubActivity:Activity(){


    protected var activityClassLoader: ClassLoader? = null
    //activity 名称
    protected var activityName = ""
    //加载插件路径
    private var pluginPath = ""
    //插件资源Manager
    private var pluginAssetManager: AssetManager? = null
    //插件 Resources
    private var pluginResources: Resources? = null
    //插件 主题
    private var pluginTheme: Resources.Theme? = null
    private var nativeLibDir: String? = null
    //dex路径
    private var dexOutPath: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nativeLibDir = File(filesDir, "pluginlib").absolutePath
        dexOutPath = File(filesDir, "dexout").absolutePath
        pluginPath = intent.getStringExtra("pluginPath").toString()
        activityName = intent.getStringExtra("activityName").toString()
        activityClassLoader = DexClassLoader(pluginPath, dexOutPath, nativeLibDir, this::class.java.classLoader)
        handlePluginResources()
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
    }

    override fun getResources(): Resources {
        return pluginResources?: super.getResources()
    }

    override fun getAssets(): AssetManager {
        return pluginAssetManager?: super.getAssets()
    }

    override fun getClassLoader(): ClassLoader {
        return activityClassLoader?: super.getClassLoader()
    }

    //处理插件资源 构造 Resources
    fun handlePluginResources(){

        fixHiddenMethod()

        try {
        pluginAssetManager = AssetManager::class.java.newInstance()
        val addAssetPathMethod = pluginAssetManager?.javaClass?.getMethod("addAssetPath", String::class.java)
        addAssetPathMethod?.invoke(pluginAssetManager, pluginPath)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //构造 Resources
        pluginResources = Resources(pluginAssetManager,super.getResources().displayMetrics,super.getResources().configuration)
        pluginTheme = pluginResources?.newTheme()
        pluginTheme?.setTo(super.getTheme())
    }

    private fun fixHiddenMethod() {
        if (SDK_INT < Build.VERSION_CODES.P) {
            return
        }

    }


}