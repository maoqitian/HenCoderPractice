package com.mao.customviewanimation

import android.animation.TypeEvaluator
import android.graphics.PointF
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * @Description: 自定义 PointF 动画完成度到属性具体值的计算公式
 * @author maoqitian
 * @date 2021/3/11 0011 18:08
 */
@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
class PointEvaluator : TypeEvaluator<PointF> {

    override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
        val startX = startValue.x
        val endX = endValue.x
        val currentX = startX + (startX - endX) * fraction
        val startY = startValue.y
        val endY = endValue.y
        val currentY = startY + (startY - endY) * fraction
        return PointF(currentX,currentY)
    }
}