package com.mao.pluginhotfixapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mao.pluginhotfixapplication.databinding.ActivityMainBinding
import okhttp3.*
import okio.buffer
import okio.sink
import okio.source
import java.io.File
import java.io.IOException
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(),View.OnClickListener {


    private lateinit var viewBinding: ActivityMainBinding

    private lateinit var hotfixDex : File

    private val remoteHotfixUrl :String= "http://pic.cdn.liangtv.cn/group1/M00/00/00/rB9ODWEJAISAQnxFAAAHDFvabMs052.dex"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //创建热修复文件
        hotfixDex = File("$cacheDir/hotfix.dex")

        viewBinding.showMessage.setOnClickListener(this)
        viewBinding.killApplication.setOnClickListener(this)
        viewBinding.loadHotfix.setOnClickListener(this)
        viewBinding.deleteHotfix.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.showMessage -> {
                viewBinding.titleMessage.text = TextUtils.getShowText()
            }
            R.id.loadHotfix -> {
                //加载修复的热更新 dex 文件
                //直接测试加载本地文件
                /*if(!hotfixDex.exists()){
                    //使用 okio 复制文件到 缓存文件中
                    assets.open("hotfix/hotfix.dex").source().use {
                        bufferSource -> hotfixDex.sink().buffer().use {
                            it.writeAll(bufferSource)
                     }
                    }
                }*/

                //网络加载
                val okHttpClient = OkHttpClient()
                val request = Request.Builder().url(remoteHotfixUrl).build()

                okHttpClient.newCall(request).enqueue(object :Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        runOnUiThread {
                            Toast.makeText(this@MainActivity,"hotfix 文件下载失败", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        hotfixDex.sink().buffer().use {
                            it.writeAll(response.body!!.source())
                        }

                        v.post {
                            Toast.makeText(this@MainActivity, "hotfix 热修复补丁文件加载成功", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                })

            }
            R.id.deleteHotfix -> {
                //清除热修复缓存
                if(hotfixDex.exists()){
                   hotfixDex.delete()
                }
            }
            R.id.killApplication -> {
                android.os.Process.killProcess(android.os.Process.myPid())
            }
        }
    }
}