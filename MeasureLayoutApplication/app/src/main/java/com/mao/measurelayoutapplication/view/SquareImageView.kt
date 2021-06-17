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
 */
class SquareImageView(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //经过父view 传入值
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        //获取较小的值 一较小的值作为正方形的边
        val minSize = min(measuredWidth,measuredHeight)

        setMeasuredDimension(minSize,minSize)
    }
}