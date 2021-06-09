package com.mao.viewxfermode.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import dp

/**
 * @Description:  xfermode 实现 多个图形的混合融合 模式测试 注意是图形区域融合
 * // 官方文档效果 注意是绘制了整个图形，包含透明背景 所以这里需要使用 bitmap 绘制图片来达到官方文档效果
 * https://developer.android.com/reference/android/graphics/PorterDuff.Mode?hl=es-419
 * @author maoqitian
 * @date 2021/3/9 0009 15:51
 */
//图片宽高
private val IMAGE_WIDTH = 150f.dp
private val IMAGE_PADDING = 100f.dp
private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)

class XfermodeView(context: Context,attrs: AttributeSet): View(context,attrs) {

    //画笔
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    //例屏缓存 区域
    val rectf = RectF(150f.dp, 50f.dp, 300f.dp, 200f.dp)

    //圆形和正方形 bitmap 对象

    val circleBitmap = Bitmap.createBitmap(IMAGE_WIDTH.toInt(),IMAGE_WIDTH.toInt(),Bitmap.Config.ARGB_8888)

    val squareBitmap = Bitmap.createBitmap(IMAGE_WIDTH.toInt(),IMAGE_WIDTH.toInt(),Bitmap.Config.ARGB_8888)

    init {
        //分别初始化绘制 圆形和正方形
        val  canvas = Canvas(circleBitmap)
        paint.color = Color.parseColor("#D81B60")
        canvas.drawOval(50f.dp, 0f.dp, 150f.dp, 100f.dp, paint)
        paint.color = Color.parseColor("#2196F3")
        canvas.setBitmap(squareBitmap)
        canvas.drawRect(0f.dp, 50f.dp, 100f.dp, 150f.dp, paint)

    }


    override fun onDraw(canvas: Canvas) {

        val saveLayerCount = canvas.saveLayer(rectf, null)

        //画圆
        /*paint.color = Color.RED
        canvas.drawOval(
            IMAGE_PADDING,
            IMAGE_PADDING, IMAGE_WIDTH + IMAGE_PADDING,
            IMAGE_WIDTH + IMAGE_PADDING,paint)*/
        //使用 bitmap 代替
        canvas.drawBitmap(circleBitmap,150f.dp,50.dp,paint)

        paint.xfermode = XFERMODE

        /*paint.color = Color.BLUE

        //画 正方形
        canvas.drawRect(IMAGE_PADDING-75f.dp,
            IMAGE_PADDING+75f.dp, IMAGE_WIDTH-75f.dp + IMAGE_PADDING,
            IMAGE_WIDTH + IMAGE_PADDING+75f.dp,paint)*/
        //使用 bitmap 代替
        canvas.drawBitmap(squareBitmap,150f.dp,50.dp,paint)



        paint.xfermode = null

        //离屏缓冲融合完成恢复回来
        canvas.restoreToCount(saveLayerCount)

    }

}