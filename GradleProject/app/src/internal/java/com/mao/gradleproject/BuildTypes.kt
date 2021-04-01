package com.mao.gradleproject

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup

/**
 * @Description:顶层函数 internal 内部版本
 * @author maoqitian
 * @date 2021/4/1 0001 16:19
 */

// Activity 扩展函数
fun Activity.addBuildIcon(){

    val iconView = View(this)
    iconView.setBackgroundColor(Color.YELLOW)
    val decorView = window.decorView as ViewGroup
    decorView.addView(iconView,100,100)
}