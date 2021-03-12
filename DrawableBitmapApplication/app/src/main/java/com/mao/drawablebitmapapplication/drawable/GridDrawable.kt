package com.mao.drawablebitmapapplication.drawable

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import androidx.core.graphics.toColorInt
import dp

/**
 * @Description: 自定义 网格 drawable
 * @author maoqitian
 * @date 2021/3/12 0012 11:07
 */

//网格间距
private val INTERVAL_LENGTH = 50.dp


class GridDrawable :Drawable(){

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = "#FFD700".toColorInt()
        strokeWidth = 8.dp
    }

    //使用 Canvas 绘制 drawable 的图像 这里绘制网格
    override fun draw(canvas: Canvas) {
        var x = bounds.left.toFloat()
        //横线绘制
        while(x <= bounds.right){
            canvas.drawLine(x,bounds.top.toFloat(),x,bounds.bottom.toFloat(),paint)
            x += INTERVAL_LENGTH
        }

        var y = bounds.top.toFloat()
        //竖线绘制
        while(y <= bounds.right){
            canvas.drawLine(bounds.left.toFloat(),y,bounds.right.toFloat(),y,paint)
            y += INTERVAL_LENGTH
        }
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getAlpha(): Int {
        return paint.alpha
    }

    //透明度
    override fun getOpacity(): Int {
        return when(paint.alpha){
            0 -> PixelFormat.TRANSPARENT //透明
            0xff -> PixelFormat.OPAQUE //不透明
            else -> PixelFormat.TRANSLUCENT //半透明
        }
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }
}