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
 * @Description: 多点触控 接力型
 * @author maoqitian
 * @date 2021/6/28 0028 11:24
 */
class MultiTouchView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

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

        when(event.actionMasked){
            MotionEvent.ACTION_DOWN -> {
                //记录下第一次按下 id
                trackId = event.getPointerId(0)
                downX = event.x
                downY = event.y
                //记录初始偏移
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }
            MotionEvent.ACTION_POINTER_DOWN->{
                //更新其他手指按下 id
                val actionIndex = event.actionIndex
                trackId = event.getPointerId(actionIndex)
                downX = event.getX(actionIndex)
                downY = event.getY(actionIndex)
                //记录初始偏移
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }
            MotionEvent.ACTION_MOVE -> {
               //移动的时候拿到移动手指 index 获取对应坐标 多点触控
                val currMoveIndex = event.findPointerIndex(trackId)
                offsetX = event.getX(currMoveIndex) - downX + originalOffsetX
                offsetY = event.getY(currMoveIndex) - downY + originalOffsetY
                //刷新
                invalidate()
            }
            MotionEvent.ACTION_POINTER_UP->{
                //判断当前离开手指是否是正在使用的手指 id

                val actionIndex = event.actionIndex
                val indexId = event.getPointerId(actionIndex)
                // 如果是该手指抬起 则需要找到一根手指进行接棒
                if(indexId == trackId){
                    //指派新的 pointer 来接管事件
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
            MotionEvent.ACTION_UP-> {
                //最后一根手指抬起无需考虑接棒
            }
        }

        return true
    }
}