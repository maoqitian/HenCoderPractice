package com.mao.motionlayoutproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionLayout.DEBUG_SHOW_PATH

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.showPath).setOnClickListener {
            findViewById<MotionLayout>(R.id.motionLayout).setDebugMode(DEBUG_SHOW_PATH)
        }
    }
}