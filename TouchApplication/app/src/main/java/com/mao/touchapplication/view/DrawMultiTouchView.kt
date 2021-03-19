package com.mao.touchapplication.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import dp
import getBitmap

/**
 * @Description: 多点触控 互相不干扰  画板例子
 * @author maoqitian
 * @date 2021/3/18 0018 10:40
 */
class DrawMultiTouchView(context: Context, attrs: AttributeSet) : View(context, attrs) {


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)


    //使用 SparseArray 来保存 每个触摸的 path
    val pathArray = SparseArray<Path>()

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5.dp
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeJoin = Paint.Join.ROUND
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //遍历绘制
        for (i in 0 until pathArray.size()){
            val path = pathArray.valueAt(i)
            canvas.drawPath(path,paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.actionMasked){
            MotionEvent.ACTION_DOWN,MotionEvent.ACTION_POINTER_DOWN->{
                //第一次按下和后面多次按下 添加path
                //获取按下的 pointer index
                var actionindex = event.actionIndex
                val path = Path()
                path.moveTo(event.getX(actionindex),event.getY(actionindex))
                pathArray.append(event.getPointerId(actionindex),path)
                invalidate()
            }
            MotionEvent.ACTION_MOVE ->{ //移动的时候遍历连线
                for (i in 0 until pathArray.size()){
                    //现获取 pointer id
                    val pointerId = event.getPointerId(i)
                    //获取对应路径
                    val path = pathArray.get(pointerId)
                    path.lineTo(event.getX(i),event.getY(i))
                }
                invalidate()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP->{
                //直接获取当前 index 从路径集合中删除对应路径
                val actionIndex = event.actionIndex
                val pointerId = event.getPointerId(actionIndex)
                pathArray.remove(pointerId)
                invalidate()
            }
        }
        return true
    }

}