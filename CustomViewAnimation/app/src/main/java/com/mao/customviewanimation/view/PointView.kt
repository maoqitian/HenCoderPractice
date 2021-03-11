package com.mao.customviewanimation.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import dp

/**
 * @Description: 小圆点 View 自定义 TypeEvaluator 自定义动画完成度计算规则
 * @author maoqitian
 * @date 2021/3/11 0011 17:57
 */
class PointView (context: Context, attr: AttributeSet) : View(context,attr){

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, android.R.color.holo_orange_dark)
        strokeWidth = 30.dp
        strokeCap = Paint.Cap.ROUND
    }

    private var pointF = PointF(10f,10f)
    set(value) {
        field = value
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPoint(pointF.x,pointF.y,paint)
    }
}