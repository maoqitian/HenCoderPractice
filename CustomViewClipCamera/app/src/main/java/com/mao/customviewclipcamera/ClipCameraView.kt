package com.mao.customviewclipcamera

import android.content.Context
import android.graphics.*
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
    private val BITMAP_WIDTH = 200.dp
    private val BITMAP_PADDING = 100.dp

    private val imageBitmap = getBitmap(BITMAP_WIDTH.toInt())

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)


    val clipPath = Path().apply {
        //区域为一个圆
        addOval(BITMAP_PADDING,BITMAP_PADDING,BITMAP_PADDING+BITMAP_WIDTH,BITMAP_PADDING+BITMAP_WIDTH,Path.Direction.CCW)

    }


    private val camera = Camera()

    init {
        camera.rotateX(30f)

        //可以设置 camera 的 Z轴值 ，可以控制投影图片的大小 防止投影过大产生模糊效果
        //还需根据手机像素密度变换配置
        camera.setLocation(0f,0f,-3f * resources.displayMetrics.density)
    }

    override fun onDraw(canvas: Canvas) {

        /**
         * 图片裁剪
         */
        //clipRect 图片处切一个小于原图的矩形
        //canvas.clipRect(BITMAP_PADDING,BITMAP_PADDING,BITMAP_PADDING+BITMAP_WIDTH/2,BITMAP_PADDING+BITMAP_WIDTH/2)

        //clipPath 图片处切一个圆，切出的圆会有毛边，直接限制精确指定像素，这是clipPath 特性 无法改变
        //canvas.clipPath(clipPath)

        /**
         * 图片几何变化 三维
         */
        //直接移动轴心默认为 X， camera 照相放大 超出屏幕范围

        //先移动 然后照相 在范围内 最后移动回原来位置(按照这个思路写则会出现问题，所以实现需要逆序思维实现)
        /*canvas.translate(BITMAP_PADDING+BITMAP_WIDTH/2,BITMAP_PADDING+BITMAP_WIDTH/2)
        camera.applyToCanvas(canvas)
        canvas.translate(-(BITMAP_PADDING+BITMAP_WIDTH/2),-(BITMAP_PADDING+BITMAP_WIDTH/2))
        canvas.drawBitmap(imageBitmap,BITMAP_PADDING,BITMAP_PADDING,paint)*/


        /**
         * 翻页水平对折 思路 范围裁切 绘制上下两部分展现翻折效果 并且应该在做变化之前裁切，不然变化之后裁切则这是是区域范围而裁切去了翻折效果
         */

        //上半部分
        canvas.save()
        canvas.translate(BITMAP_PADDING+BITMAP_WIDTH/2,BITMAP_PADDING+BITMAP_WIDTH/2)
        //移动之后 裁切一半 直接裁切则后面操作都会裁切 没有意义 需配合 canvas.save() canvas.restore()
        canvas.clipRect(-BITMAP_WIDTH/2,-BITMAP_WIDTH/2,
            BITMAP_WIDTH/2,0f)
        canvas.translate(-(BITMAP_PADDING+BITMAP_WIDTH/2),-(BITMAP_PADDING+BITMAP_WIDTH/2))
        canvas.drawBitmap(imageBitmap,BITMAP_PADDING,BITMAP_PADDING,paint)
        canvas.restore()

        //下半部分 绘制
        canvas.save()
        canvas.translate(BITMAP_PADDING+BITMAP_WIDTH/2,BITMAP_PADDING+BITMAP_WIDTH/2)
        camera.applyToCanvas(canvas)
        //移动之后 裁切一半
        canvas.clipRect(-BITMAP_WIDTH/2,0f,
            BITMAP_WIDTH/2,BITMAP_WIDTH/2)
        canvas.translate(-(BITMAP_PADDING+BITMAP_WIDTH/2),-(BITMAP_PADDING+BITMAP_WIDTH/2))
        canvas.drawBitmap(imageBitmap,BITMAP_PADDING,BITMAP_PADDING,paint)
        canvas.restore()

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
