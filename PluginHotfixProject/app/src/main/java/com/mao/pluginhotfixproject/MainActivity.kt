package com.mao.pluginhotfixproject

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import dalvik.system.BaseDexClassLoader
import dalvik.system.DexClassLoader
import kotlinx.android.synthetic.main.activity_main.*
import okio.buffer
import okio.sink
import okio.source
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class MainActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showMessage.setOnClickListener(this)
        loadHotfix.setOnClickListener(this)
        deleteHotfix.setOnClickListener(this)
        killApplication.setOnClickListener(this)
    }

    override fun onClick(v: View) {

        when(v.id){
            R.id.showMessage ->{
                title_message.text = TitleUtils.getHotfixMessage()
            }
            R.id.loadHotfix ->{
                //加载修复的热更新 文件
                //创建缓存 apk 文件
                val hotfixApk =
                    File("$cacheDir/hotfix.apk")
                if(!hotfixApk.exists()){
                    //使用 okio 复制文件到 缓存文件中
                    try {
                        assets.open("hotfix.apk").source().buffer()
                            .use { bufferSource -> hotfixApk.sink().buffer().use {
                                    bufferedSink -> bufferedSink.writeAll(bufferSource) }
                            }
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }



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

                //替换 dexElements字段
                dexElementsField.set(originalPathListObject,dexElementsObject)

            }
            R.id.deleteHotfix ->{

            }
            R.id.killApplication ->{

            }
        }
    }
}