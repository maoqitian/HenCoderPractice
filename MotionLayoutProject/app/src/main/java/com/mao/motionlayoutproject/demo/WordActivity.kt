package com.mao.motionlayoutproject.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout.DEBUG_SHOW_PATH
import com.mao.motionlayoutproject.databinding.ActivityWordBinding

/**
 * 字母适用 Motion 关键帧 动画效果
 */
class WordActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityWordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityWordBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.showDebug.setOnClickListener {
            viewBinding.motionLayout.setDebugMode(DEBUG_SHOW_PATH)
        }

    }
}