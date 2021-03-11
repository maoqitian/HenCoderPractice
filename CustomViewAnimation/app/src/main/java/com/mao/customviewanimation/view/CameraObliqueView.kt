package com.mao.customviewanimation.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withSave
import com.mao.customviewanimation.R
import dp

/**
 * @Description: 自定义View 斜着切 图片 集合变化
 * @author maoqitian
 * @date 2021/3/10 0010 13:26
 */

class CameraObliqueView (context: Context, attributeSet: AttributeSet): View(context,attributeSet) {


    //图片宽度
    private val BITMAP_WIDTH = 200.dp
    private val BITMAP_PADDING = 100.dp

    private val imageBitmap = getBitmap(BITMAP_WIDTH.toInt())

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val camera = Camera()

    //初始角度
    private var topFlip = 0f
    set(value) {
        field = value
        invalidate()
    }
    //底部翻折角度
    private var bottomFlip = 30f
        set(value) {
            field = value
            invalidate()
        }
    //翻折角度
    private var flipRotate = 30f
        set(value) {
            field = value
            invalidate()
        }

    init {

        //可以设置 camera 的 Z轴值 ，可以控制投影图片的大小 防止投影过大产生模糊效果
        //还需根据手机像素密度变换配置
        camera.setLocation(0f,0f,-6f * resources.displayMetrics.density)
    }

    override fun onDraw(canvas: Canvas) {

        /**
         * 翻页水平对折 思路 范围裁切 绘制上下两部分展现翻折效果 并且应该在做变化之前裁切，不然变化之后裁切则这是是区域范围而裁切去了翻折效果
         */

        //上半部分
        canvas.withSave {
            canvas.translate(BITMAP_PADDING + BITMAP_WIDTH / 2, BITMAP_PADDING + BITMAP_WIDTH / 2)
            //斜着切需要调整一个角度也就是旋转 注意旋转之后要旋转回来，并且扩大裁切范围 否则只能裁切部分不完整
            canvas.rotate(-flipRotate)
            //保存状态
            camera.save()
            //下方翻折角度，属性动画 每次重绘都需要进行变化调用
            camera.rotateX(topFlip)
            //camera 映射到 画布上
            camera.applyToCanvas(canvas)
            camera.restore() //应用之后恢复
            //移动之后 裁切一半 直接裁切则后面操作都会裁切 没有意义 需配合 canvas.save() canvas.restore()
            canvas.clipRect(
                -BITMAP_WIDTH, -BITMAP_WIDTH,
                BITMAP_WIDTH, 0f
            )
            canvas.rotate(flipRotate)
            canvas.translate(
                -(BITMAP_PADDING + BITMAP_WIDTH / 2),
                -(BITMAP_PADDING + BITMAP_WIDTH / 2)
            )
            canvas.drawBitmap(imageBitmap, BITMAP_PADDING, BITMAP_PADDING, paint)
        }
        //下半部分 绘制
        canvas.withSave {
            canvas.translate(BITMAP_PADDING+BITMAP_WIDTH/2,BITMAP_PADDING+BITMAP_WIDTH/2)
            canvas.rotate(-flipRotate)
            //保存状态
            camera.save()
            //下方翻折角度，属性动画 每次重绘都需要进行变化调用
            camera.rotateX(bottomFlip)
            //camera 映射到 画布上
            camera.applyToCanvas(canvas)
            camera.restore() //应用之后恢复
            //移动之后 裁切一半
            canvas.clipRect(-BITMAP_WIDTH,0f,
                BITMAP_WIDTH,BITMAP_WIDTH)
            canvas.rotate(flipRotate)
            canvas.translate(-(BITMAP_PADDING+BITMAP_WIDTH/2),-(BITMAP_PADDING+BITMAP_WIDTH/2))
            canvas.drawBitmap(imageBitmap,BITMAP_PADDING,BITMAP_PADDING,paint)
        }

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
