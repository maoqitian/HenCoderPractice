package com.mao.leakcanarytest

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.util.Printer
import android.view.Choreographer
import androidx.annotation.RequiresApi
import com.mao.leakcanarytest.databinding.ActivityMainBinding
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        Debug.startMethodTracing("testTraceView")
        super.onCreate(savedInstanceState)

        Trace.beginSection("onCreate")
        //ViewBinding 方式绑定 view
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tv.setOnClickListener {
            //触发内存泄漏
            LeakTest(this).leak()
            finish()
        }
        SystemClock.sleep(3000)
        Trace.endSection()


        var start = true
        var startTime :Long= 0
        Looper.getMainLooper().setMessageLogging(object :Printer{
            override fun println(x: String?) {
                if(start){
                    start = false
                    startTime = System.currentTimeMillis()
                }else{
                    start = true
                    Log.e("Monitor","监测耗时："+(System.currentTimeMillis() - startTime > 100))
                }
            }

        })

        // vsync 每16ms 发送一次 触发编舞者 的 doFrame 方法
        // 可以获取两次绘制的耗时 知道是否有卡段发送 但是不能知道是哪个地方卡顿
        Choreographer.getInstance().postFrameCallback(choreographerCallback)



        val stringBuilder = StringBuilder()
        Thread.currentThread().stackTrace.forEach { stackTraceElement ->
            stringBuilder.append(stackTraceElement.toString()).append("\n")
        }

        Log.e("stackTrace",stringBuilder.toString())


        Debug.stopMethodTracing()


        mainLooper.queue.addIdleHandler(object :MessageQueue.IdleHandler {
            override fun queueIdle(): Boolean {
                memoryData()
                return true
            }
        })
    }

    fun memoryData(){
        val runtime = Runtime.getRuntime()
        val javaMax: Long = runtime.maxMemory()
        val javaTotal: Long = runtime.totalMemory()
        val javaUsed: Long = javaTotal - runtime.freeMemory()
        // Java 内存使用超过最大限制的 85%
        val proportion =  javaUsed / javaMax

        Log.e("stackTrace","proportion : $proportion")

    }

    //对象回收会调用 该方法
    protected fun finalize(){
        println("maoqitian  MainActivity finalize......")
    }


    fun doSomething(){
       a()
       b()
       c()
    }

    fun a(){
          SystemClock.sleep(750)
    }
    fun b(){
        SystemClock.sleep(15)
    }
    fun c(){
        SystemClock.sleep(150)
    }


    val choreographerCallback = MyFrameCallback()


    inner class MyFrameCallback :Choreographer.FrameCallback{
        var lastTime :Long = 0
        override fun doFrame(frameTimeNanos: Long) {
            if(lastTime.toInt() == 0){
                lastTime = frameTimeNanos
            }else{
                val times = (frameTimeNanos - lastTime)/1000000

                val frames = (times/16).toInt()

                if (times > 16){
                    Log.w("maoqitian", "UI线程超时(超过16ms):" + times + "ms" + " , 丢帧:" + frames);
                }

                lastTime = frameTimeNanos
            }

            Choreographer.getInstance().postFrameCallback(choreographerCallback)
        }
    }
}