package com.mao.multipointerproject.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import dp
import getBitmap

/**
 * @Description: 多点触控第二种类型 配合型 多个手指作用于同一个中心点
 * @author maoqitian
 * @date 2021/8/20 0020 8:58
 */
class MultiPointerView2(context: Context?, attrs: AttributeSet?) : View(context, attrs) {


    val imageBitmap = getBitmap(resources,200.dp.toInt())

    var paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var offsetX = 0f
    var offsetY = 0f

    var downX = 0f
    var downY = 0f

    //原本的偏移量
    var originalOffsetX = 0f
    var originalOffsetY = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //需要减去手指相对于图片左上角的偏移量

        canvas.drawBitmap(imageBitmap,offsetX,offsetY,paint)
    }


    /**
     * 接力型多点触控，第一个手指正常按下，另一根手指按下则将当前触摸事件让这个按下的手指接管
     * 也就是接力型多点触控
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {


        //遍历每个点累加 对应 x 和 y 值除以对应的点数则为中心点坐标
        var currentFocusX = 0f
        var currentFocusY = 0f

        var sumX = 0f
        var sumY = 0f

        //手指触摸个数
        var pointerCount = event.pointerCount

        val isPointerUp = event.actionMasked == MotionEvent.ACTION_POINTER_UP

        for (i in 0 until pointerCount){
            if(!(isPointerUp && i == event.actionIndex)){
                sumX += event.getX(i)
                sumY += event.getY(i)
            }
        }

        if(isPointerUp){
            pointerCount--
        }

        currentFocusX = sumX/pointerCount
        currentFocusY = sumY/pointerCount


        when(event.actionMasked){
            MotionEvent.ACTION_DOWN,MotionEvent.ACTION_POINTER_DOWN,MotionEvent.ACTION_POINTER_UP->{
                //记录按下位置
                downX = currentFocusX
                downY = currentFocusY
                //同时记录下原始的偏移 保证移动时候获取位置正确
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }
            MotionEvent.ACTION_MOVE->{
                //计算偏移位置
                offsetX = currentFocusX - downX + originalOffsetX
                offsetY = currentFocusY - downY + originalOffsetY
                //标记失效 下次 vsync 信号到来重绘
                invalidate()
            }
        }

        //消费触摸事件
        return true
    }
}
