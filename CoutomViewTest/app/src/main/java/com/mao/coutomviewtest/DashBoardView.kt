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
 *  description : 仪表盘View
 */

//圆弧半径
private var RADIUS = 150f.dptopx
//圆弧指针长度

private var LENGTH = 120F.dptopx


//圆弧开角度值
var OPEN_ANGLE = 120f

//刻度的 宽度 和 长
private val DASH_WIDTH = 2f.dptopx
private val DASH_LENGTH = 10f.dptopx


class DashBoardView(context: Context?, attr: AttributeSet?) : View(context,attr) {

    //画笔

    var paint = Paint(Paint.ANTI_ALIAS_FLAG) //抗锯齿画笔
    //弧度的path
    var path = Path()
    //使用 path 来画 刻度
    var dash = Path()

    private lateinit var patheffect:PathDashPathEffect

    init {
        //画笔样式 边长 空心
        paint.strokeWidth = 4f.dptopx
        paint.style = Paint.Style.STROKE
        //添加路径区域
        dash.addRect(0f,0f, DASH_WIDTH, DASH_LENGTH,Path.Direction.CCW)

    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //弧度 使用 path 方便测量
        path.reset()
        path.addArc(width/2f - RADIUS,height/2f-RADIUS,width/2f + RADIUS,height/2f+RADIUS,
            90f + OPEN_ANGLE / 2f , 360 - OPEN_ANGLE )
        //获取长度才好测量间隔
        var pathMeasure = PathMeasure(path,false)
        //获取长度 设置间隔 pathMeasure.length/20f  20个间隔
        patheffect = PathDashPathEffect(dash,(pathMeasure.length- DASH_WIDTH)/20f,0f,PathDashPathEffect.Style.ROTATE)

    }


    override fun onDraw(canvas: Canvas) {
        //画圆弧

        canvas.drawPath(path,paint)

        //画刻度
        //advance phase 参数相反
        paint.pathEffect = patheffect
        canvas.drawPath(path,paint)
        paint.pathEffect = null

        //画指针 通过三角函数 计算 x y 终点位置 90+ OPEN_ANGLE/2f + (360 - OPEN_ANGLE)/20f 初始角度 加 20刻度每个刻度的角度就是 指针的角度
        //计算刻度 2
        canvas.drawLine(width/2f,height/2f,
            width/2+ LENGTH * cos(indexToRadians(2)).toFloat(),
            height/2+ LENGTH * sin(indexToRadians(2)).toFloat()
            ,paint)
    }

    //指针角度转换 参数为 指向第几个刻度
    private fun indexToRadians(index :Int): Double {
       return  Math.toRadians((90+ OPEN_ANGLE/2f + (360 - OPEN_ANGLE)/20f*index).toDouble())
    }
}