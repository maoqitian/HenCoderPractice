package com.mao.multitouchpointapplication.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import dp

/**
 * @Description: 画板 多点触控 各自互相不干扰
 * @author maoqitian
 * @date 2021/6/29 0029 15:52
 */
class DrawBoardView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 5.dp
        //画线 圆头、圆角
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }

    //记录多个点 path
    var paths = SparseArray<Path>()


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //画线 多点对应多个 path 遍历绘制
        for (i in 0 until paths.size()){
            val path = paths.valueAt(i)
            canvas.drawPath(path,paint)
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {

        when(event.actionMasked){
            MotionEvent.ACTION_DOWN,MotionEvent.ACTION_POINTER_DOWN -> {
                //按下 移动 path 到对应点
                //有新的点触摸 创建新的 path
                val actionIndex = event.actionIndex
                val path = Path()
                path.moveTo(event.getX(actionIndex),event.getY(actionIndex))
                //保存 path
                paths.append(event.getPointerId(actionIndex),path)
                invalidate()
            }
            MotionEvent.ACTION_MOVE->{
                for (i in 0 until paths.size()){
                    //获取每个手指 id
                    val pointerId = event.getPointerId(i)
                    //获取path
                    val path = paths.get(pointerId)
                    //连线
                    path.lineTo(event.getX(i),event.getY(i))
                }
                invalidate()
            }
            MotionEvent.ACTION_UP->{
                //清空
                val actionIndex = event.actionIndex
                val pointerId = event.getPointerId(actionIndex)
                paths.remove(pointerId)
                invalidate()
            }
        }


        return true
    }

}