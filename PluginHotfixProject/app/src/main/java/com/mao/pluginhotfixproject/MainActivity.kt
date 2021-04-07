package com.mao.pluginhotfixproject

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import okio.Okio
import okio.buffer
import okio.sink
import java.io.File
import java.io.IOException


class MainActivity : AppCompatActivity(),View.OnClickListener {


    //远端 hotfix 地址 http://pic.cdn.liangtv.cn/group1/M00/00/00/rB9ODWBtJXCASUouAAACvEi3VPk892.dex
    private lateinit var hotfixApk :File

    val remoteUrl = "http://pic.cdn.liangtv.cn/group1/M00/00/00/rB9ODWBtJXCASUouAAACvEi3VPk892.dex"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //热修复文件路径
        hotfixApk = File("$cacheDir/hotfix-classes.dex")

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
                //直接加载本地文件
                /*if(!hotfixApk.exists()){
                    //使用 okio 复制文件到 缓存文件中
                    try {
                        assets.open("hotfix-classes.dex").source().buffer()
                            .use { bufferSource -> hotfixApk.sink().buffer().use {
                                    bufferedSink -> bufferedSink.writeAll(bufferSource) }
                            }
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }*/
                //可以选择从网络加载
                val okHttpClient = OkHttpClient()

                val request = Request.Builder().url(remoteUrl).build()

                okHttpClient.newCall(request).enqueue(object :Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        Toast.makeText(this@MainActivity,"hot fix 文件下载失败",Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        /*val bufferSink = hotfixApk.sink().buffer()
                        bufferSink.write(response.body!!.bytes())*/

                        try {
                            hotfixApk.sink().buffer()
                                .use { sink -> sink.write(response.body!!.bytes()) }
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                        v.post {
                            Toast.makeText(this@MainActivity, "hot fix 补丁文件加载成功", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                })
            }
            R.id.deleteHotfix ->{
                if (hotfixApk.exists()){
                    hotfixApk.delete()
                }
            }
            R.id.killApplication ->{
                android.os.Process.killProcess(android.os.Process.myPid())
            }
        }
    }
}