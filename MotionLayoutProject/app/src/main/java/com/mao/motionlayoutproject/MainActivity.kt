package com.mao.motionlayoutproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionLayout.DEBUG_SHOW_PATH
import androidx.transition.TransitionManager
import java.util.concurrent.atomic.AtomicInteger

/**
 * 自定义联系效果
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.showPath).setOnClickListener {
            findViewById<MotionLayout>(R.id.motionLayout).setDebugMode(DEBUG_SHOW_PATH)
        }

        //TransitionManager.beginDelayedTransition()

        Thread().start()

        Thread.interrupted()

        val atomicInteger = AtomicInteger()

        atomicInteger.getAndIncrement()
    }
}