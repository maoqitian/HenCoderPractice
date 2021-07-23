package com.mao.transitiondemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
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

        //关键帧 KeyTrigger 可触发回调
        viewBing.simpleMotion.addTransitionListener(object :TransitionAdapter(){
            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
                super.onTransitionTrigger(motionLayout, triggerId, positive, progress)
                Toast.makeText(this@MotionSimpleActivity,"MotionLayout onTransitionTrigger triggerId：" +
                        "$triggerId progress：$progress positive：$positive",Toast.LENGTH_SHORT).show()
            }
        })
    }
}