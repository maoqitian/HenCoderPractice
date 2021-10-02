package com.mao.pluginloadapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mao.pluginloadapplication.activity.ainterface.StubInterfaceActivity
import com.mao.pluginloadapplication.activity.hook.HookAppInstrumentation
import com.mao.pluginloadapplication.activity.hook.PluginContext
import com.mao.pluginloadapplication.activity.ref.StubReflectActivity
import com.mao.pluginloadapplication.databinding.ActivityMainBinding
import dalvik.system.DexClassLoader
import okio.*
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    lateinit var  pluginApkFile:File

    //加载插件 activity 路径名称
    private lateinit var activityName: String

    //插件 classloader
    private lateinit var pluginClassLoader: DexClassLoader


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        init()

        //反射分发
        activityMainBinding.btStartRef.setOnClickListener {
           StubReflectActivity.startPluginActivity(this,pluginApkFile.absolutePath,activityName)
        }

        //接口分发
        activityMainBinding.btStartInterface.setOnClickListener {
            StubInterfaceActivity.startPluginActivity(this,pluginApkFile.absolutePath,activityName)
        }

        //接口分发
        activityMainBinding.btStartHook.setOnClickListener {
            val intent = Intent()
            //直接使用插件 Activity
            intent.setClass(this, pluginClassLoader.loadClass(activityName))
            startActivity(intent)
        }

    }

    private fun init() {
        loadPlugin()

        activityName = "com.mao.plugin.PluginActivity"

        //通过 classLoader 加载 apk 中dex 文件
        //在通过反射调用类方法
        pluginClassLoader = DexClassLoader(pluginApkFile.path,cacheDir.path,null,this::class.java.classLoader)

        //Hook 注入替换 Instrumentation

        //HookAppInstrumentation.inject(this,PluginContext(pluginApkFile.absolutePath,this,application,pluginClassLoader))

        //不对反射的类有任何引用 使用 class.forName , 使用  DexClassLoader.loadClass 替换
        //拿到对应的类

        /*val pluginUtilsClass = dexClassLoader.loadClass("com.mao.plugin.utils.PluginUtils")
        //获取pluginUtils构造方法
        val declaredConstructor = pluginUtilsClass.declaredConstructors[0]
        //解除构造方法限制
        declaredConstructor.isAccessible = true
        //调用构造方法
        val constructor = declaredConstructor.newInstance()

        //获取需要调用的方法
        val pluginLoadMethod = pluginUtilsClass.getDeclaredMethod("pluginLoad")

        //解除pluginLoad方法限制
        pluginLoadMethod.isAccessible = true
        //调用pluginLoad方法
        pluginLoadMethod.invoke(constructor)*/




    }

    private fun loadPlugin() {
        //创建缓存文件 接收复制加载的 apk
        pluginApkFile = File( "$cacheDir/plugin.apk")

        //使用 okio 复制文件到 缓存文件中
        try {
            assets.open("plugin.apk").source().buffer().use { bufferSource ->
                pluginApkFile.sink().buffer().use { bufferedSink ->
                    bufferedSink.writeAll(bufferSource)
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}