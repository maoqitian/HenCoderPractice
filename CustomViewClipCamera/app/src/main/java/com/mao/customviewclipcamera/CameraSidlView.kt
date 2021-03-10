package com.mao.customviewclipcamera

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import dp

/**
 * @Description: 斜着切
 * @author maoqitian
 * @date 2021/3/10 0010 13:26
 */

class CameraSidlView (context: Context, attributeSet: AttributeSet): View(context,attributeSet) {


    //图片宽度
    private val BITMAP_WIDTH = 200.dp
    private val BITMAP_PADDING = 100.dp

    private val imageBitmap = getBitmap(BITMAP_WIDTH.toInt())

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val camera = Camera()

    init {
        camera.rotateX(30f)

        //可以设置 camera 的 Z轴值 ，可以控制投影图片的大小 防止投影过大产生模糊效果
        //还需根据手机像素密度变换配置
        camera.setLocation(0f,0f,-3f * resources.displayMetrics.density)
    }

    override fun onDraw(canvas: Canvas) {

        /**
         * 翻页水平对折 思路 范围裁切 绘制上下两部分展现翻折效果 并且应该在做变化之前裁切，不然变化之后裁切则这是是区域范围而裁切去了翻折效果
         */

        //上半部分
        canvas.save()
        canvas.translate(BITMAP_PADDING+BITMAP_WIDTH/2,BITMAP_PADDING+BITMAP_WIDTH/2)
        //斜着切需要调整一个角度也就是旋转 注意旋转之后要旋转回来，并且扩大裁切范围 否则只能裁切部分不完整
        canvas.rotate(-30f)
        //移动之后 裁切一半 直接裁切则后面操作都会裁切 没有意义 需配合 canvas.save() canvas.restore()
        canvas.clipRect(-BITMAP_WIDTH,-BITMAP_WIDTH,
            BITMAP_WIDTH,0f)
        canvas.rotate(30f)
        canvas.translate(-(BITMAP_PADDING+BITMAP_WIDTH/2),-(BITMAP_PADDING+BITMAP_WIDTH/2))
        canvas.drawBitmap(imageBitmap,BITMAP_PADDING,BITMAP_PADDING,paint)
        canvas.restore()

        //下半部分 绘制
        canvas.save()
        canvas.translate(BITMAP_PADDING+BITMAP_WIDTH/2,BITMAP_PADDING+BITMAP_WIDTH/2)
        canvas.rotate(-30f)
        //camera 映射到 画布上
        camera.applyToCanvas(canvas)
        //移动之后 裁切一半
        canvas.clipRect(-BITMAP_WIDTH,0f,
            BITMAP_WIDTH,BITMAP_WIDTH)
        canvas.rotate(30f)
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
