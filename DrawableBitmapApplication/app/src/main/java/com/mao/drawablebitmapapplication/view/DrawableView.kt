package com.mao.drawablebitmapapplication.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.mao.drawablebitmapapplication.drawable.GridDrawable
import dp

/**
 * @Description: 测试自定义 drawable
 * @author maoqitian
 * @date 2021/3/12 0012 11:08
 */
class DrawableView(context: Context, attr: AttributeSet) : View(context,attr){

    val gridDrawable = GridDrawable()

    override fun onDraw(canvas: Canvas) {

        //需要给drawable 设置区域
        gridDrawable.setBounds(40.dp.toInt(),50.dp.toInt(),300.dp.toInt(),300.dp.toInt())

        gridDrawable.draw(canvas)

    }
}