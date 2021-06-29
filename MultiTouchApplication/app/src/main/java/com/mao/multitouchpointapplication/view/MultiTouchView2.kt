package com.mao.multitouchpointapplication.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import dp
import getBitmap

/**
 * @Description: 多点触控 协作型 多个点作用围绕统一中心点
 * @author maoqitian
 * @date 2021/6/28 0028 11:24
 */
class MultiTouchView2(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    val bitmapImage = getBitmap(resources,250.dp.toInt())


    var offsetX = 0f
    var offsetY = 0f

    var downX = 0f
    var downY = 0f

    var originalOffsetX = 0f
    var originalOffsetY = 0f

    //目前第几个手指起作用 id
    var trackId = 0


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //先绘制一个最简单的图形
        //偏移量需要减去初始按下位置
        canvas.drawBitmap(bitmapImage,offsetX,offsetY,paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //遍历每个点累加 对应 x 和 y 值除以对应的点数则为中心点坐标
        var centerX = 0f
        var centerY = 0f
        var sumX = 0f
        var sumY = 0f

        //当前触摸点个数
        var pointerCount = event.pointerCount
        //如果抬起 则去除该点累加 避免触控跳动
        val isPointerUp = event.actionMasked == MotionEvent.ACTION_POINTER_UP
        //遍历累加 x y 坐标值
        for (i in 0 until event.pointerCount){
            if(!(isPointerUp && i == event.actionIndex)){
                sumX += event.getX(i)
                sumY += event.getY(i)
            }
        }

        //也要减去忽略的 pointerCount次数
        if(isPointerUp){
            pointerCount--
        }
        centerX = sumX/pointerCount
        centerY = sumY/pointerCount
        when(event.actionMasked){
            //不管是抬起 还是按下都需要记录偏移值和初始值
            MotionEvent.ACTION_DOWN,MotionEvent.ACTION_POINTER_UP,MotionEvent.ACTION_POINTER_DOWN-> {
                //记录下第一次按下 id
                downX = centerX
                downY = centerY
                //记录初始偏移
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }
            MotionEvent.ACTION_MOVE -> {
                //根据中心点刷新
                offsetX = centerX - downX + originalOffsetX
                offsetY = centerY - downY + originalOffsetY
                //刷新
                invalidate()
            }
        }

        return true
    }
}