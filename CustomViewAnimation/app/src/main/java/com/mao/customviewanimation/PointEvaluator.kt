package com.mao.customviewanimation

import android.animation.TypeEvaluator
import android.graphics.PointF

/**
 * @Description: 自定义 PointF 动画完成度到属性具体值的计算公式
 * @author maoqitian
 * @date 2021/3/11 0011 18:08
 */
class PointEvaluator : TypeEvaluator<PointF> {

    override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
        val startX = startValue.x
        val endX = endValue.x
        val currentX = startX + (endX - startX) * fraction
        val startY = startValue.y
        val endY = endValue.y
        val currentY = startY + (endY - startY) * fraction
        return PointF(currentX,currentY)
    }
}