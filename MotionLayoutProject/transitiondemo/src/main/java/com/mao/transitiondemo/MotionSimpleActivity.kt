package com.mao.transitiondemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mao.transitiondemo.databinding.ActivityMotionSimpleLayoutBinding


/**
 * 自定义 Motion scene
 */
class MotionSimpleActivity : AppCompatActivity() {

    //使用viewbing
    lateinit var viewBing : ActivityMotionSimpleLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBing = ActivityMotionSimpleLayoutBinding.inflate(layoutInflater)
        setContentView(viewBing.root)
    }
}