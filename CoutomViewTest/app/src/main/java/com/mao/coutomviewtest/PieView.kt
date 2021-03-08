package com.mao.coutomviewtest

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.mao.coutomviewtest.extentds.dptopx
import kotlin.math.cos
import kotlin.math.sin

/**
 *  author : maoqitian
 *  date : 2021/3/7 22:53
 *  description : 饼图 View
 */

//圆弧半径
private var RADIUS = 100f.dptopx

//角度
private val ANGLES = floatArrayOf(60f, 30f, 180f, 90f)
//颜色值
private val COLORS = listOf(Color.parseColor("#EAC100"), Color.parseColor("#FF2D2D"), Color.parseColor("#D9FFFF"), Color.parseColor("#28FF28"))
//饼图选中偏移值
private  val OFFSET_LENGTH = 10f.dptopx
class PieView(context: Context?, attr: AttributeSet?) : View(context,attr) {

    //画笔

    var paint = Paint(Paint.ANTI_ALIAS_FLAG) //抗锯齿画笔


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

    }


    override fun onDraw(canvas: Canvas) {

        //开始角度
        var startAngles = 0f



        for ((index ,angle) in ANGLES.withIndex()){
            paint.color = COLORS[index]


            if (index == 3 ){
                canvas.save()
                //偏移 还是计算三角函数 正弦余弦
                //偏移 OFFSET_LENGTH * cos(Math.toRadians(startAngle + angle / 2f.toDouble())).toFloat()
                //需要注意角度是当前角度加上之前移动的值
                canvas.translate(OFFSET_LENGTH * cos(Math.toRadians(startAngles + angle / 2f.toDouble())).toFloat(),
                    OFFSET_LENGTH * sin(Math.toRadians(startAngles + angle / 2f.toDouble())).toFloat())
            }

            //画扇形
            canvas.drawArc(width/2f - RADIUS,height/2f - RADIUS,width/2f + RADIUS,height/2f + RADIUS,
                startAngles,angle,true,paint)

            //每次画完一个扇形更新 初始化角度值
            startAngles += angle

            if (index == 3){
                canvas.restore()
            }
        }



    }

}