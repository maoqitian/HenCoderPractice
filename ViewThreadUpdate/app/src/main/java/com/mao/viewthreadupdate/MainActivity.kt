package com.mao.viewthreadupdate

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.os.SystemClock
import android.view.SurfaceHolder
import android.view.WindowManager
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * 子线程更新UI
         */
        /**
         * 方法一  主线程调用 view.requestLayout()，设置了 PFLAG_FORCE_LAYOUT，则 View 更新 UI 时会判断 该标志位为PFLAG_FORCE_LAYOUT，
         * 则不会再次调用父的 requestLayout，也就不会触发 ViewRootImpl 的 requestLayout 方法，也就不会触发线程检查
         */
       /* tvText.setOnClickListener {
            it.requestLayout()
            //直接子线程更新
            thread {
                tvText.text = "${Thread.currentThread()},${SystemClock.uptimeMillis()}"
            }
        }*/
        /**
         * 方法二
         * 将 ViewRootImpl thread 指向子线程
         */
       /* tvText.setOnClickListener {
            //直接子线程更新
            thread {
                Looper.prepare()
                val button = Button(this)
                windowManager.addView(button,WindowManager.LayoutParams())
                button.setOnClickListener {
                    button.text = "${Thread.currentThread()},${SystemClock.uptimeMillis()}"
                }
                Looper.loop()
            }
        }*/
        /**
         * 方法三
         * 将 TextView 设置固定宽高 在 setText 方法中判断支持硬件加速绘制方法，而跳过了 requestLayout 方法调用，
         * 然后调用 invalidate方法，该方法没有检查线程判断
         * 如果去除硬件加速则会报错
         * android:hardwareAccelerated="false"
         * 自定义 View 设置了大小也同时开启硬件加速也同样
         */
        tvText.setOnClickListener {
            //直接子线程更新
            thread {
                tvText.text = "${Thread.currentThread()},${SystemClock.uptimeMillis()}"
            }
        }
        /**
         * 方法四
         * 使用 surfaceView 来绘制
         * SurfaceView 支持子线程更新
         */
        surfaceview.holder.addCallback(object :SurfaceHolder.Callback{
            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
            }

            override fun surfaceCreated(holder: SurfaceHolder) {

                thread {
                    while (true){
                        val canvas = holder.lockCanvas()
                        val  random = Random()
                        val r = random.nextInt(255)
                        val g = random.nextInt(255)
                        val b = random.nextInt(255)
                        canvas.drawColor(Color.rgb(r,g,b))
                        holder.unlockCanvasAndPost(canvas)
                        SystemClock.sleep(200)
                    }
                }

            }

        })

    }
}