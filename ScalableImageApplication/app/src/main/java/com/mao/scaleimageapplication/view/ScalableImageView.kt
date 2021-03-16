package com.mao.scaleimageapplication.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.mao.scaleimageapplication.R
import dp

/**
 * @Description: 可缩放双向滑动的 图片加载浏览自定义View
 * @author maoqitian
 * @date 2021/3/16 0016 16:04
 */
class ScalableImageView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    var IMAGE_SIZE = 200.dp.toInt()
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    //图片起始坐标
    var startX = 0f
    var startY = 0f

    //缩放比
    var minScale = 0f
    var maxScale = 0f

    //加载的图片
    private val bitmapImage = getBitmap(IMAGE_SIZE)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //屏幕宽度 - 减去图片宽度 除2 保证图片中心显示
        startX = ((width - IMAGE_SIZE)/2).toFloat()
        startY = ((height - IMAGE_SIZE)/2).toFloat()

        //根据图片与屏幕宽高比值计算 缩放比
        if (bitmapImage.width/bitmapImage.height.toFloat() > width/height.toFloat()){
            //图片宽高比 大于屏幕宽高比
            minScale = width / bitmapImage.width.toFloat()
            maxScale = height / bitmapImage.height.toFloat()
        }else {
            minScale = height / bitmapImage.height.toFloat()
            maxScale = width / bitmapImage.width.toFloat()
        }

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.scale(minScale,minScale,width/2f,height/2f)
        canvas.drawBitmap(bitmapImage,startX,startY,paint)
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