package com.mao.customviewmeasuretext.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import dp

/**
 *  author : maoqitian
 *  date : 2021/3/9 21:43
 *  description : 文字测量 运动界面
 */


private var RADIUS = 150f.dp

class SportMeasureTextView(context: Context,attributeSet: AttributeSet):View(context,attributeSet) {


    //画笔

    var paint = Paint(Paint.ANTI_ALIAS_FLAG) //抗锯齿画笔



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

    }

}