package com.mao.pluginhotfixapplication.application

import android.app.Application
import android.content.Context
import dalvik.system.BaseDexClassLoader
import dalvik.system.DexClassLoader
import java.io.File

/**
 * 将加载了热修复dex文件的 classLoader 的 pathList 的 dexElements 加载到当前应用的 dexElements 前面，提前加载修复bug
 * @Description:热修复 Application
 * @author maoqitian
 * @date 2021/8/3 0003 9:34
 */
class PluginHotFixApplication :Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        //创建缓存 apk 文件
        val hotfixDex = File("$cacheDir/hotfix.dex")

        /**
         * 当前应用 classLoader 反射
         */
        //获取当前应该的 ClassLoader
        val currClassLoader = classLoader

        //获取当前的 父ClassLoader class
        val baseDexClass = BaseDexClassLoader::class.java
        //反射获取 父ClassLoader 的 pathList 字段
        val pathListField = baseDexClass.getDeclaredField("pathList")
        //解除限制
        pathListField.isAccessible = true
        //获取 当前应用 classLoader PathListObject
        val originalPathListObject = pathListField.get(currClassLoader)

         /*
         * 热修复文件 classLoader 反射
         */
        //创建加载热修复 dex 包的 ClassLoader
        val hotfixDexClassLoader = DexClassLoader(hotfixDex.path, cacheDir.path, null, null)
        //获取 自行创建的 ClassLoader  pathList 对象
        val hotfixPathListObject = pathListField.get(hotfixDexClassLoader)
        //获取 hotfixPathList class
        val pathListClass = hotfixPathListObject.javaClass
        //获取 hotfix 的 dexElements 字段
        val dexElementsField = pathListClass.getDeclaredField("dexElements")
        dexElementsField.isAccessible = true
        //获取 hotfixDexElements 对象
        val hotfixDexElementsObject = dexElementsField.get(hotfixPathListObject)

        //获取原本 的 DexElements 对象
        val originalDexElementsObject = dexElementsField.get(originalPathListObject)

        //合并 hotfixDexElements 对象数据到 originalDexElementsObject
        //dexElements 数组合并
        val oldLength: Int = java.lang.reflect.Array.getLength(originalDexElementsObject)
        val newLength: Int = java.lang.reflect.Array.getLength(hotfixDexElementsObject)
        //创建新的 合并后的数组对象
        val concatDexElementsObject: Any =
            java.lang.reflect.Array.newInstance(hotfixDexElementsObject.javaClass.componentType, oldLength + newLength)
        //新数据线插入到数组当中
        for (i in 0 until newLength) {
            java.lang.reflect.Array.set(concatDexElementsObject, i, java.lang.reflect.Array.get(hotfixDexElementsObject, i))
        }
        //老数组数据放到新数组中
        for (i in 0 until oldLength) {
            java.lang.reflect.Array.set(
                concatDexElementsObject,
                newLength + i,
                java.lang.reflect.Array.get(originalDexElementsObject, i)
            )
        }
        //新数组替换原本的 DexElements 数组
        dexElementsField[originalPathListObject] = concatDexElementsObject
    }

    override fun onCreate() {
        super.onCreate()
    }
}