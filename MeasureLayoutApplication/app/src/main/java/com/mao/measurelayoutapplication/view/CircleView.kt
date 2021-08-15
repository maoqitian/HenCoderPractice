package com.mao.measurelayoutapplication.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import dp

/**
 *  author : maoqitian
 *  date : 2021/3/14 13:11
 *  description : 画圆 能够根据开发者传入期望的值判断大小
 */
class CircleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {


    val paint = Paint(Paint.ANTI_ALIAS_FLAG)


    var radius = 100.dp

    var padding = 50.dp

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //当前 view 期望大小
        var viewSize = ((radius + padding) *2).toInt()

        //重新判断大小是否合适 固定代码 系统提供
        //如果有开发者期望则使用开发者的期望值

        val width = resolveSize(viewSize,widthMeasureSpec)
        val height = resolveSize(viewSize,heightMeasureSpec)

        setMeasuredDimension(width,height)

    }


    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(radius + padding,radius + padding,radius,paint)
    }
}