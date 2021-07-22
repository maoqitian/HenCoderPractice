package com.mao.transitiondemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.transition.TransitionManager

class ObjectAnimatorActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_object_animator2)

    }

    //过渡动画
    //原理：两个场景之间的过渡，⼀个「开始场景」⼀个「结束场景」
    fun onClick2(view: View) {
        TransitionManager.beginDelayedTransition(view.parent as ViewGroup)
        with(view.layoutParams as LinearLayout.LayoutParams) {
            gravity = Gravity.CENTER
            height *= 2
            width *= 2
        }

        view.requestLayout()
    }
}