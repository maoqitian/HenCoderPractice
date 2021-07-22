package com.mao.transitiondemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.transition.TransitionManager

class ObjectAnimatorActivity : AppCompatActivity() {

    lateinit var root: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_object_animator)

        root = findViewById(R.id.root)
    }

    fun onClick(view: View) {

        //使用过渡动画执行
        TransitionManager.beginDelayedTransition(root)
        with(view.layoutParams as FrameLayout.LayoutParams) {
            gravity = Gravity.CENTER
            height *= 2
            width *= 2
        }

        view.requestLayout()
    }
}