package com.mao.customviewanimation.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.mao.customviewanimation.R
import dp

/**
 * @Description: 圆形 view
 * @author maoqitian
 * @date 2021/3/11 0011 15:35
 */
class CircleView(context: Context,attr: AttributeSet) :View(context,attr){


    var radiusCircle = 30.dp
    set(value) {
        field = value
        //属性值改变 需要调用 invalidate() 重绘 要不然属性动画在屏幕上看不到效果
        invalidate() //使其标记为失效 下一次 vsync 刷新则重绘
    }

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.colorPrimary)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(width/2f,height/2f,radiusCircle,paint)
    }


}