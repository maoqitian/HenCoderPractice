package com.mao.customviewclipcamera

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import dp

/**
 * @Description: 范围裁切 几何变换 、 clipRect() 、 Canvas.translate 、 Matrix几何变换 、 camera 三维旋转
 * @author maoqitian
 * @date 2021/3/10 0010 13:26
 */

class ClipCameraView (context: Context, attributeSet: AttributeSet): View(context,attributeSet) {


    //图片宽度
    private val BITMAP_WIDTH = 120.dp
    private val BITMAP_PADDING = 100.dp

    private val imageBitmap = getBitmap(BITMAP_WIDTH.toInt())

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(imageBitmap,BITMAP_PADDING,BITMAP_PADDING,paint)

    }


    //根据指定的宽度加载 bitmap
    private fun getBitmap(width : Int): Bitmap {
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
