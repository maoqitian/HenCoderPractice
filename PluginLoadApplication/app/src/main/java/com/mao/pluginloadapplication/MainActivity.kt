package com.mao.pluginloadapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dalvik.system.DexClassLoader
import okio.*
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //创建缓存文件 接收复制加载的 apk
        val pluginApkFile = File( "$cacheDir/plugin1.apk")

        //使用 okio 复制文件到 缓存文件中
        try {
            assets.open("loadapk/plugin.apk").source().buffer().use { bufferSource ->
                pluginApkFile.sink().buffer().use { bufferedSink ->
                    bufferedSink.writeAll(bufferSource)
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }


        //通过 classLoader 加载 apk 中dex 文件
        //在通过反射调用类方法
        val dexClassLoader = DexClassLoader(pluginApkFile.path,cacheDir.path,null,null)

        //不对反射的类有任何引用 使用 class.forName , 使用  DexClassLoader.loadClass 替换
        //拿到对应的类

        val pluginUtilsClass = dexClassLoader.loadClass("com.mao.plugin.utils.PluginUtils")
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
        pluginLoadMethod.invoke(constructor)
    }
}