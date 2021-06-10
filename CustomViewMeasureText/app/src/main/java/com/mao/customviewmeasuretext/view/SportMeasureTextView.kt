package com.mao.customviewmeasuretext.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.mao.customviewmeasuretext.R
import dp

/**
 *  author : maoqitian
 *  date : 2021/3/9 21:43
 *  description : 文字测量 运动界面
 */


private var RADIUS = 120f.dp

private val DRAW_TEXT = "75%"

class SportMeasureTextView(context: Context,attributeSet: AttributeSet):View(context,attributeSet) {


    //画笔 抗锯齿画笔
    var paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 110.dp
        typeface = ResourcesCompat.getFont(context, R.font.font)
        isFakeBoldText = true
        /**
         * 文字水平居中
         */
        textAlign = Paint.Align.CENTER
    }

    //存储测量绘制文字区域
    private val textBoundsRect = Rect()
    //防止字体改变跳动，使用相对中间合适的位置
    private val textFontMetrics = Paint.FontMetrics()

    override fun onDraw(canvas: Canvas) {

        //画圆
        paint.style = Paint.Style.STROKE
        paint.color = Color.LTGRAY
        paint.strokeWidth = 20.dp
        canvas.drawCircle(width/2f,height/2f, RADIUS,paint)

        //画圆弧
        paint.color = Color.CYAN
        //结尾的样式
        paint.strokeCap = Paint.Cap.ROUND

        canvas.drawArc(width/2f- RADIUS,height/2f- RADIUS,width/2f+ RADIUS,height/2f+ RADIUS,
            -90f,255f,false,paint)

        /**
         * 文字绘制 1  居中
         */
        //改变画笔
        paint.style = Paint.Style.FILL
        paint.color = Color.RED

        //居中的纵向测量
        /**
         * 静态文字可以使用 上下边界平局值来纠正高度的偏移 (textBoundsRect.top + textBoundsRect.bottom)/2
         */
        //通过 getTextBounds 来获取文字边界
        /*paint.getTextBounds(DRAW_TEXT,0,DRAW_TEXT.length,textBoundsRect)
        canvas.drawText(DRAW_TEXT,width/2f,height/2f - (textBoundsRect.top + textBoundsRect.bottom)/2,paint)*/


        //动态文字 则使用 FontMetrics
        paint.getFontMetrics(textFontMetrics)
        canvas.drawText(DRAW_TEXT,width/2f,height/2f - (textFontMetrics.ascent + textFontMetrics.descent)/2,paint)

        /**
         * 文字绘制2 文字贴边 左右对齐
         */

       /* paint.textAlign = Paint.Align.LEFT

        paint.getFontMetrics(textFontMetrics)
        paint.getTextBounds(DRAW_TEXT,0,DRAW_TEXT.length,textBoundsRect)

        //默认是 baseline 与左边对齐所以显示会在界面外部，则需要减去文字自身 top 距离
        //左上贴边对齐
        //TextBounds 方法
        canvas.drawText(DRAW_TEXT,-textBoundsRect.left.toFloat(), -textBoundsRect.top.toFloat() ,paint)
        //FontMetrics 方法
        canvas.drawText(DRAW_TEXT,0f, -textFontMetrics.ascent ,paint)
*/
    }

}