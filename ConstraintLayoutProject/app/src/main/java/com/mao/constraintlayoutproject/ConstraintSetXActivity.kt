package com.mao.constraintlayoutproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.sample_constraint_start.*

/**
 * 变化一组 view
 */

class ConstraintSetXActivity : AppCompatActivity() {

    //水平排列变为网格排列
    //说白了就是布局替换
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sample_constraint_start)

        tempLayout.setOnClickListener {
            val constraintLayout = it as ConstraintLayout
            val constraintSet = ConstraintSet().apply {
                isForceId = false //不强制布局每个控件都有 id
                //布局克隆
                clone(this@ConstraintSetXActivity,R.layout.sample_constraint_end)
            }
            TransitionManager.beginDelayedTransition(constraintLayout)
            //应用变化
            constraintSet.applyTo(constraintLayout)
        }
    }

}