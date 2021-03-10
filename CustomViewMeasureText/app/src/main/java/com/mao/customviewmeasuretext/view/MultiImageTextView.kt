package com.mao.customviewmeasuretext.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.mao.customviewmeasuretext.R
import dp

/**
 * @Description: 多行 Text 和 image 展示
 * @author maoqitian
 * @date 2021/3/10 0010 13:26
 */


class MultiImageTextView (context: Context, attributeSet: AttributeSet): View(context,attributeSet) {

    var textpaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 15.dp
        typeface = ResourcesCompat.getFont(context, R.font.font)
    }

    var paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 15.dp
        typeface = ResourcesCompat.getFont(context, R.font.font)
    }

    var bitmapPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)

    //图片宽度
    private val BITMAP_WIDTH = 120.dp
    //图片距离顶部高度
    private val BITMAP_PADDING = 60.dp

    val imageBitmap = getBitmap(BITMAP_WIDTH.toInt())

    //绘制文字
    private val text = "A Gradle plugin packages up reusable pieces of build logic, " +
            "which can be used across many different projects and builds. " +
            "Gradle allows you to implement your own plugins, so you can reuse " +
            "your build logic, and share it with others.You can implement a " +
            "Gradle plugin in any language you like, provided the implementation ends" +
            " up compiled as JVM bytecode. In our examples, we are going to use Java as " +
            "the implementation language for standalone plugin project and Groovy or " +
            "Kotlin in the buildscript plugin examples. In general, a plugin implemented using " +
            "Java or Kotlin, which are statically typed, will perform better than the same plugin implemented using Groovy." +
            "You can include the source for the plugin directly in the build script. This has the benefit that the plugin is " +
            "automatically compiled and included in the classpath of the build script without you having to do anything. " +
            "However, the plugin is not visible outside the build script, and so you cannot reuse the plugin outside the build " +
            "script it is defined in"

    private val fontMetrics = Paint.FontMetrics()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //使用系统提供的类绘制一段文字
        /*val staticLayout = StaticLayout(text,textpaint,width,Layout.Alignment.ALIGN_NORMAL,1f,0f,false)
        staticLayout.draw(canvas)*/
        //图片绘制
        canvas.drawBitmap(imageBitmap,width -BITMAP_WIDTH,BITMAP_PADDING,bitmapPaint)
        //文字绘制

        //使用 FontMetrics 测量文字边界
        paint.getFontMetrics(fontMetrics)
        //使用breakText 是文字换行
        val measureFloatWith = floatArrayOf(0f)
        //每一行初始位置
        var textLineStart = 0
        //每一行需要绘制的长度
        var count:Int
        //每一个便宜位置，初始为减去上边界高度
        var textLineOffset = -fontMetrics.top

        //每一行宽度 需要判断是否与图片重合
        var textMaxWith : Float

        while (textLineStart < text.length){
            textMaxWith = if(textLineOffset + fontMetrics.bottom < BITMAP_PADDING  // 文字顶部小于图片顶部
                || textLineOffset + fontMetrics.top > BITMAP_PADDING+BITMAP_WIDTH){ //文字底部大于图片顶部
                //全部宽度
                width.toFloat()
            }else{
                //重合 减去图片宽度
                width.toFloat() - BITMAP_WIDTH
            }
            //测量
            count =  paint.breakText(text,textLineStart,text.length,true,textMaxWith,measureFloatWith)
            //绘制
            canvas.drawText(text,textLineStart,textLineStart + count,0f,textLineOffset,paint)
            //更新初始位置
            textLineStart += count
            textLineOffset += paint.fontSpacing //系统提供默认行间距
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
