package com.mao.multipointerproject.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import androidx.core.util.forEach
import dp

/**
 * @Description: 画板 多点触控第三种类型 各自为战型 每个手指自己处理自己的
 * @author maoqitian
 * @date 2021/8/20 0020 11:15
 */
class DrawBoardView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {


    //画笔
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
        //遍历绘制多条线
        for (i in 0 until paths.size()){
            val path = paths.valueAt(i)
            canvas.drawPath(path,paint)
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {

        when(event.actionMasked){
            MotionEvent.ACTION_POINTER_DOWN,MotionEvent.ACTION_DOWN ->{
               //不管多少个手指按下 增加画线 path
                val actionIndex = event.actionIndex
                //创建新的 path
                val path = Path()
                path.moveTo(event.getX(actionIndex),event.getY(actionIndex))
                //保存path key 是 Pointer Id
                paths.append(event.getPointerId(actionIndex),path)
                invalidate()
            }
            MotionEvent.ACTION_MOVE ->{
                //获取每个 path 连线
                for (i in 0 until paths.size()){
                    val pointerId = event.getPointerId(i)
                    val path = paths.get(pointerId)
                    path.lineTo(event.getX(i),event.getY(i))
                }
                invalidate()
            }
            MotionEvent.ACTION_UP,MotionEvent.ACTION_POINTER_UP ->{
                //手指抬起 清空画板
                val actionIndex = event.actionIndex
                val pointerId = event.getPointerId(actionIndex)
                paths.remove(pointerId)
                invalidate()
            }
        }

        return true
    }

    fun cleanPath(){

    }
}