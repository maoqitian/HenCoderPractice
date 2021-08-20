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
 * @Description: 多点触控第一种类型 接力型
 * @author maoqitian
 * @date 2021/8/20 0020 8:58
 */
class MultiPointerView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {


    val imageBitmap = getBitmap(resources,200.dp.toInt())

    var paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var offsetX = 0f
    var offsetY = 0f

    var downX = 0f
    var downY = 0f

    //原本的偏移量
    var originalOffsetX = 0f
    var originalOffsetY = 0f

    var trackerId = 0

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

        when(event.actionMasked){
            MotionEvent.ACTION_DOWN->{
                //记录按下手指的 pointer id
                //默认第一根手指 id 为 0
                trackerId = event.getPointerId(0)
                //记录按下位置
                downX = event.x
                downY = event.y
                //同时记录下原始的偏移 保证移动时候获取位置正确
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }
            MotionEvent.ACTION_POINTER_DOWN->{
                //多个手指按下
                //首先获取的是按下的 index id
                val actionIndex = event.actionIndex
                //根据 id 找到对应的 pointer id
                trackerId = event.getPointerId(actionIndex)

                //更新按下位置和原始偏移位置
                downX = event.getX(actionIndex)
                downY = event.getY(actionIndex)
                //同时记录下原始的偏移 保证移动时候获取位置正确
                originalOffsetX = offsetX
                originalOffsetY = offsetY

            }
            MotionEvent.ACTION_MOVE->{
                //通过前面记录的 trackerId 来获取对应 Pointer 手指的 id
                val actionIndex = event.findPointerIndex(trackerId)
                //计算偏移位置
                offsetX = event.getX(actionIndex) - downX + originalOffsetX
                offsetY = event.getY(actionIndex) - downY + originalOffsetY
                //标记失效 下次 vsync 信号到来重绘
                invalidate()
            }
            MotionEvent.ACTION_POINTER_UP->{
                //手指抬起，则先需要判断是否是当前正在处理事件的 Pointer 如果是则需要找到新的 Pointer 接力
                val actionIndex = event.actionIndex
                //获取当前抬起手指的 Pointer id
                val upPointerId = event.getPointerId(actionIndex)

                if (upPointerId == trackerId){
                    //找到新的手指接管
                    var newActionIndex = if (upPointerId == event.pointerCount -1){
                        //当前为最后一个 index 则需向前指派一位
                        event.pointerCount - 2
                    }else{
                        //否则获取最后一个来指派
                        event.pointerCount - 1
                    }
                    //重新记录接管点 id
                    trackerId = event.getPointerId(newActionIndex)

                    //重新记录偏移量
                    downX = event.getX(newActionIndex)
                    downY = event.getY(newActionIndex)
                    //同时记录下原始的偏移 保证移动时候获取位置正确
                    originalOffsetX = offsetX
                    originalOffsetY = offsetY
                }
            }
        }

        //消费触摸事件
        return true
    }
}
