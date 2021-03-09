package com.mao.viewxfermode.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import dp

/**
 * @Description:  xfermode 实现 多个图形的混合融合 模式测试 注意是图形区域融合
 * @author maoqitian
 * @date 2021/3/9 0009 15:51
 */
//图片宽高
private val IMAGE_WIDTH = 150f.dp
private val IMAGE_PADDING = 100f.dp
private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

class XfermodeView(context: Context,attrs: AttributeSet): View(context,attrs) {

    //画笔
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)


    val rectf = RectF(0f.dp, 0f.dp, 400f.dp, 400f.dp)

    override fun onDraw(canvas: Canvas) {

        val saveLayerCount = canvas.saveLayer(rectf, null)

        //画圆
        paint.color = Color.RED
        canvas.drawOval(
            IMAGE_PADDING,
            IMAGE_PADDING, IMAGE_WIDTH + IMAGE_PADDING,
            IMAGE_WIDTH + IMAGE_PADDING,paint)
        paint.xfermode = XFERMODE

        paint.color = Color.BLUE

        //画 正方形
        canvas.drawRect(IMAGE_PADDING-75f.dp,
            IMAGE_PADDING+75f.dp, IMAGE_WIDTH-75f.dp + IMAGE_PADDING,
            IMAGE_WIDTH + IMAGE_PADDING+75f.dp,paint)
        paint.xfermode = null

        //离屏缓冲融合完成恢复回来
        canvas.restoreToCount(saveLayerCount)

    }

}