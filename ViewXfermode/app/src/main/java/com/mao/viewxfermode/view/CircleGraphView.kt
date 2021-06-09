package com.mao.viewxfermode.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.mao.viewxfermode.R
import dp

/**
 * @Description: 圆形 View 使用 xfermode 实现 多个图形的混合融合
 * @author maoqitian
 * @date 2021/3/9 0009 15:51
 */
//图片宽高
private val IMAGE_WIDTH = 150f.dp
private val IMAGE_PADDING = 20f.dp
private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

class CircleGraphView(context: Context,attrs: AttributeSet): View(context,attrs) {

    //画笔
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)


    //离屏缓冲 裁剪区域 就是画圆的区域
    val rectf = RectF(
        IMAGE_PADDING,
        IMAGE_PADDING, IMAGE_WIDTH + IMAGE_PADDING,
        IMAGE_WIDTH + IMAGE_PADDING
    )

    override fun onDraw(canvas: Canvas) {

        //思路 先画一个圆 再画一个图像 使用 xfermode 融合使用 圆形图形和显示图像就变成圆形图像
        //需要使用 离屏缓冲来执行融合
        //截取一段区域 离屏缓冲
        val saveLayerCount = canvas.saveLayer(rectf, null)
        //画圆
        canvas.drawOval(
            IMAGE_PADDING,
            IMAGE_PADDING, IMAGE_WIDTH + IMAGE_PADDING,
            IMAGE_WIDTH + IMAGE_PADDING,paint)
        paint.xfermode = XFERMODE
        //画图 drawBitmap
        canvas.drawBitmap(getBitmap(IMAGE_WIDTH.toInt()),
            IMAGE_PADDING,
            IMAGE_PADDING,paint)
        //设置完使用完 离屏缓冲后置空
        paint.xfermode = null
        //离屏缓冲融合完成恢复回来
        canvas.restoreToCount(saveLayerCount)
    }


    //根据指定的宽度加载 bitmap
    private fun getBitmap(width : Int):Bitmap{
        val options = BitmapFactory.Options()
        //只读取尺寸
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources,
            R.drawable.avatar,options)
        options.inJustDecodeBounds = false
        //获取原本尺寸比
        options.inDensity = options.outWidth
        //根据输入宽度加载
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources,
            R.drawable.avatar,options)
    }
}