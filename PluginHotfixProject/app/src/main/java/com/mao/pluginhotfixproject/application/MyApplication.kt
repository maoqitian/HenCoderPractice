package com.mao.pluginhotfixproject.application

import android.app.Application
import android.content.Context
import dalvik.system.BaseDexClassLoader
import dalvik.system.DexClassLoader
import java.io.File

/**
 * @Description: 在应用启动前进行插件反射加载操作 防止不生效
 * @author maoqitian
 * @date 2021/4/7 0007 10:03
 */
class MyApplication :Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        //创建缓存 apk 文件
        val hotfixApk =
            File("$cacheDir/hotfix-classes.dex")

        //获取 当前类的 classloader
        var originalClassLoader = classLoader
        //创建加载的classLoader
        var dexClassLoader = DexClassLoader(hotfixApk.path, cacheDir.path, null, null)

        var loadClass = BaseDexClassLoader::class.java
        //反射获取 ClassLoader 的 pathList 字段 然后替换 dexElements 字段
        val pathListField = loadClass.getDeclaredField("pathList")
        //解除限制
        pathListField.isAccessible = true
        //获取 自行创建的 ClassLoader  pathList 对象
        val pathListObject = pathListField.get(dexClassLoader)
        //获取 pathListClass
        val pathListClass =  pathListObject.javaClass
        //获取 dexElements字段
        val dexElementsField = pathListClass.getDeclaredField("dexElements")
        dexElementsField.isAccessible = true
        //dexElements对象
        val dexElementsObject =  dexElementsField.get(pathListObject)

        //获取原本的 ClassLoader  pathList 对象
        val originalPathListObject = pathListField.get(originalClassLoader)
        //获取原本的 dexElements 对象
        val originalDexElementsObject = dexElementsField.get(originalPathListObject)

        //dexElements 数组合并
        val oldLength: Int = java.lang.reflect.Array.getLength(originalDexElementsObject)
        val newLength: Int = java.lang.reflect.Array.getLength(dexElementsObject)
        val concatDexElementsObject: Any =
            java.lang.reflect.Array.newInstance(dexElementsObject.javaClass.componentType, oldLength + newLength)
        for (i in 0 until newLength) {
            java.lang.reflect.Array.set(concatDexElementsObject, i, java.lang.reflect.Array.get(dexElementsObject, i))
        }
        for (i in 0 until oldLength) {
            java.lang.reflect.Array.set(
                concatDexElementsObject,
                newLength + i,
                java.lang.reflect.Array.get(originalDexElementsObject, i)
            )
        }

        //替换 将 hotfix dexElements 值替换原本的 dexElements 中
        dexElementsField[originalPathListObject] = concatDexElementsObject

       }


}