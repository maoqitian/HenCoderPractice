package com.mao.measurelayoutapplication.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import java.lang.StringBuilder
import kotlin.math.min
import kotlin.properties.Delegates


/**
 *  author : maoqitian
 *  date : 2021/3/14 12:50
 *  description : 正方形的 Image View，使用系统测量的结果
 *  View 尺寸 自定义第一种方式 ， 简单根据测量好的数据改写View
 */
class SquareImageView(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //经过父view 传入值
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //在此父View 可以获取的测量结果从而不影响其他子View的测量和摆放
        //获取较小的值 一较小的值作为正方形的边
        val minSize = min(measuredWidth,measuredHeight)

        setMeasuredDimension(minSize,minSize)
    }
}