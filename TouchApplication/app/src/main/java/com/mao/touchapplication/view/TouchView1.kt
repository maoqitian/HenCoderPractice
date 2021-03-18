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
 * @Description: 接力型 触摸
 * @author maoqitian
 * @date 2021/3/18 0018 10:40
 */
class TouchView1(context: Context, attrs: AttributeSet) : View(context, attrs) {


    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    val bitmapImage = getBitmap(resources,150.dp.toInt())

    //按下坐标
    var downX = 0f
    var downY = 0f
    //偏移坐标
    var offsetX = 0f
    var offsetY = 0f

    var originalOffsetX = 0f
    var originalOffsetY = 0f

    //当前拖动的手指
    var trackId = 0

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmapImage,offsetX,offsetY,paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.actionMasked){
            MotionEvent.ACTION_DOWN ->{
               //获取 初始按下 id
               trackId = event.getPointerId(0)
               downX = event.x
               downY = event.y
               //记录按下图片的初始偏移 保证第二次按的时候移动正确
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }
            MotionEvent.ACTION_POINTER_DOWN ->{
               //获取第二根手指按下的 id
                val actionIndex = event.actionIndex
                trackId = event.getPointerId(actionIndex)
                //根据 event.findPointerIndex(trackId) 获取当前的 第二根手指按下值
                downX = event.getX(actionIndex)
                downY = event.getY(actionIndex)
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }
            MotionEvent.ACTION_MOVE ->{
                //无法获取当前正在移动的手指，伪命题
                //减去按下的值保证跟随手指移动
                //到此移动实时获取正在移动手指的 index
                var currentIndex = event.findPointerIndex(trackId)
                offsetX = event.getX(currentIndex) - downX + originalOffsetX
                offsetY = event.getY(currentIndex) - downY + originalOffsetY
                invalidate()
            }
            MotionEvent.ACTION_POINTER_UP->{
                // 非最后一根手指抬起
                //当前index
                val actionIndex = event.actionIndex
                val indexId = event.getPointerId(actionIndex)
                //有手指抬起指派新的 pointer 来接管事件
                if(trackId == indexId){
                    //抬起 id 是正在追踪的 id
                    var newIndex = if(indexId == event.pointerCount -1){
                        //当前为最后一个 index 则需向前指派一位
                        event.pointerCount - 2
                    }else {
                        event.pointerCount - 1
                    }
                    //重置接管点
                    trackId = event.getPointerId(newIndex)
                    downX = event.getX(newIndex)
                    downY = event.getY(newIndex)
                    //记录按下图片的初始偏移 保证第二次按的时候移动正确
                    originalOffsetX = offsetX
                    originalOffsetY = offsetY
                }
            }
            MotionEvent.ACTION_UP ->{

            }

        }
        return true
    }

}