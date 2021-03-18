package com.mao.touchapplication.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import dp
import getBitmap

/**
 * @Description: 协作性 共同 触摸 作用于共同中心点
 * @author maoqitian
 * @date 2021/3/18 0018 10:40
 */
class MultiTouchView2(context: Context, attrs: AttributeSet) : View(context, attrs) {


    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    val bitmapImage = getBitmap(resources,150.dp.toInt())

    //按下坐标
    var downX = 0f
    var downY = 0f
    //偏移坐标
    var offsetX = 0f
    var offsetY = 0f

    //初始偏移
    var originalOffsetX = 0f
    var originalOffsetY = 0f

    //当前拖动的手指
    var trackId = 0

    //中心点坐标
    var centerFocusX = 0f
    var centerFocusY = 0f


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmapImage,offsetX,offsetY,paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        var sumX = 0f
        var sumY = 0f
        var pointerCount = event.pointerCount
        //is
        val isPointerUp = event.actionMasked == MotionEvent.ACTION_POINTER_UP
        for (i in 0 until event.pointerCount){
            //需要去除向上抬起的动作事件
            if(!(isPointerUp && i == event.actionIndex)){
                sumX = event.getX(i)
                sumY = event.getY(i)
            }
        }
        //同时总数分母也要减一
        if(isPointerUp){
            pointerCount --
        }
        //中心点坐标等于 每个触控点 总和除以触控点个数
        centerFocusX = sumX /pointerCount
        centerFocusY = sumY /pointerCount


        when(event.actionMasked){
            MotionEvent.ACTION_DOWN,MotionEvent.ACTION_POINTER_DOWN,MotionEvent.ACTION_POINTER_UP ->{
               downX = centerFocusX
               downY = centerFocusY
               //记录按下图片的初始偏移 保证第二次按的时候移动正确
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }
            MotionEvent.ACTION_MOVE ->{
                //减去按下的值保证跟随手指移动
                offsetX = centerFocusX - downX + originalOffsetX
                offsetY = centerFocusY - downY + originalOffsetY
                invalidate()
            }


        }
        return true
    }

}